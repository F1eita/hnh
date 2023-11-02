package com.example.lesson_4_zhuravleva

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.lang.AssertionError
import kotlin.coroutines.ContinuationInterceptor

class SomeViewModel: ViewModel() {

    val text: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    fun setText(text: String){
        this.text.value = text
    }

    init{
        dispatcherDefault()
    }

    private fun log(msg: String){
        Log.d("coroutine_test", msg)
    }

    private suspend fun alpha(){
        log("alpha() in")
        delay(1000)
        log("alpha() out")
    }

    private suspend fun alphaThreadInfo(){
        log("alpha() in "
                + Thread.currentThread().toString()
        )
        delay(1000)
        log("alpha() out "
                + Thread.currentThread().toString())
    }

    private fun coroutineStartDefault(){
        viewModelScope.launch{
            log("init() in")

            val job = Job()
            val coroutine = launch(
                context = job,
                start = CoroutineStart.DEFAULT
            ){
                alpha()
            }

            log("CoroutineDEFAULT: $coroutine")
            log("JobDEFAULT: $job")

            log("init() out")
        }
    }

    /*В выводе: CoroutineATOMIC: StandaloneCoroutine{Cancelling}@12a812
                JobATOMIC: JobImpl{Cancelled}@7133be3
      В примере корутина и job в состоянии active*/
    private fun coroutineStartAtomic(){
        viewModelScope.launch{
            log("init() in")

            val job = Job()
            job.cancel()
            val coroutine = launch(
                context = job,
                start = CoroutineStart.ATOMIC
            ){
                alpha()
            }

            log("CoroutineATOMIC: $coroutine")
            log("JobATOMIC: $job")

            log("init() out")
        }
    }

    /*В выводе: CoroutineLAZY: LazyStandaloneCoroutine{New}@12a812
      В примере корутина в состоянии active*/
    private fun coroutineStartLazy(){
        viewModelScope.launch{
            log("init() in")

            val job = Job()
            val coroutine = launch(
                context = job,
                start = CoroutineStart.LAZY
            ){
                alpha()
            }

            log("CoroutineLAZY: $coroutine")
            log("JobLAZY: $job")
            coroutine.join()
            log("init() out")
        }
    }

    /*В выводе: init() in Thread[DefaultDispatcher-worker-1,5,main]
                alpha() in Thread[main,5,main]
                alpha() out Thread[main,5,main]
                init() out Thread[DefaultDispatcher-worker-1,5,main]
     В примере в выводе корутина на потоке еще одного диспетчера(второго), а не на главном
     +viewmodelscope перешёл с первого диспетчера на второй диспетчер,
     хотя должен быть в своём*/
    private fun coroutineStartDefaultThreads(){
        viewModelScope.launch(Dispatchers.Default){
            log("init() in "
                + Thread.currentThread().toString())

            val coroutine = launch(
                context = Dispatchers.Main,
                start = CoroutineStart.DEFAULT
            ){
                alphaThreadInfo()
            }
            coroutine.join()
            log("init() out "
                    + Thread.currentThread().toString())
        }
    }

    private fun coroutineStartUndispatchedThreads(){
        viewModelScope.launch(Dispatchers.Default){
            log("init() in "
                    + Thread.currentThread().toString())

            val coroutine = launch(
                context = Dispatchers.Main,
                start = CoroutineStart.UNDISPATCHED
            ){
                alphaThreadInfo()
            }
            coroutine.join()
            log("init() out "
                    + Thread.currentThread().toString())
        }
    }

    private fun coroutineInfo(){
        viewModelScope.launch {
            log("CoroutineName: "
             + coroutineContext[CoroutineName].toString())
            log("Job: "
                    + coroutineContext[Job].toString())
            log("ContinuationInterceptor: "
                    + coroutineContext[ContinuationInterceptor].toString())
            log("CoroutineExceptionHandler: "
                    + coroutineContext[CoroutineExceptionHandler].toString())
        }
    }

    /*При отмене детей parentJob остаётся активной*/
    private fun fathersAndSons(){
        viewModelScope.launch{
            val parentJob = launch {
                val childJobAlpha = launch{
                    log("childJobAlpha: in")
                    repeat(1000){
                        log("childJobAlpha step: $it")
                        delay(1000L)
                    }
                    log("childJopAlpha: out")
                }
                childJobAlpha.invokeOnCompletion {
                    log("childJopAlpha: ${it?.message}")
                }

                val childJobBravo = launch{
                    log("childJobBravo: in")
                    repeat(1000){
                        log("childJobBravo step: $it")
                        delay(1000L)
                    }
                    log("childJopBravo: out")
                }
                childJobBravo.invokeOnCompletion {
                    log("childJopBravo: ${it?.message}")
                }

                delay(10000)
            }
            delay(1500)
            parentJob.cancelChildren()
            log("parentJob isActive: ${parentJob.isActive}")
        }
    }

    private fun fathersAndSonsWithHandler(){
        viewModelScope.launch{
            val handler = CoroutineExceptionHandler{ _, throwable ->
                log("Caught ${throwable.message}")
            }
            val parentJob = launch(handler + SupervisorJob()){
                try {
                    val childJobAlpha = launch(SupervisorJob()){
                        log("childJobAlpha: in")
                        repeat(1000){
                            log("childJobAlpha step: $it")
                            delay(1000L)
                        }
                        log("childJopAlpha: out")
                    }
                }  catch (t: Throwable){
                    log("childJobAlpha: ${t.message}")
                }

                try {
                    val childJobBravo = launch{
                        log("childJobBravo: in")
                        delay(1000)
                        throw AssertionError("Forced exception")
                    }
                }  catch (t: Throwable){
                    log("childJobBravo: ${t.message}")
                }
            }
            parentJob.invokeOnCompletion {
                log("parentJob: ${it?.message}")
            }
            delay(1500)
            log("parentJob isActive: ${parentJob.isActive}")
        }
    }

    private fun fatherAndSonsWithAsyncAwait(){
        viewModelScope.launch{
            val parentJob = launch(SupervisorJob()){
                val childJobBravo = async(SupervisorJob()){
                    log("childJobBravo: in")
                    delay(1000)
                    throw AssertionError("Forced exception")
                }
                try {
                    childJobBravo.await()
                }  catch (t: Throwable){
                    log("Caught: ${t.message}")
                }
                log("parentJob() out")
            }
            parentJob.invokeOnCompletion {
                log("parentJob: ${it?.message}")
            }
            delay(1500)
            log("parentJob isActive: ${parentJob.isActive}")
        }
    }

    private fun twoViewModelScope(){
        val handler = CoroutineExceptionHandler{ _, throwable ->
            log("Caught ${throwable.message}")
        }
        viewModelScope.launch(handler){
            try {
                val childJobAlpha = launch(SupervisorJob()){
                    log("childJobAlpha: in")
                    repeat(1000){
                        delay(1000)
                        log("childJobAlpha step: $it")
                    }
                }
            }  catch (t: Throwable){
                log("Caught: ${t.message}")
            }
        }

        viewModelScope.launch(handler){
            try {
                val childJobBravo = launch{
                    log("childJobBravo: in")
                    delay(1000)
                    throw AssertionError("Forced exception")
                }
            }  catch (t: Throwable){
                log("Caught: ${t.message}")
            }
        }
    }

    private fun longCoroutine(){
        viewModelScope.launch {
            val veryLongJob = launch{
                try{
                    log("veryLongJob() before delay")
                    delay(Long.MAX_VALUE)
                    log("veryLongJob() after delay")
                } finally{
                    log("veryLongJob() cancelled")
                }
            }
            log("veryLongJob() cancelling")
            yield()
            veryLongJob.cancelAndJoin()

            log("veryLongJob() isActive: ${veryLongJob.isActive}")
            log("parentJob() isActive: $isActive")
        }
    }

    private fun dispatcherUnconfined(){
        viewModelScope.launch(Dispatchers.Unconfined){
            log("1: " + Thread.currentThread().toString())
            delay(1000L)
            log("2: " + Thread.currentThread().toString())
            withContext(Dispatchers.Main){
                delay(1000L)
                log("3: " + Thread.currentThread().toString())
                withContext(Dispatchers.Unconfined){
                    log("4: " + Thread.currentThread().toString())
                    delay(1000L)
                    log("5: " + Thread.currentThread().toString())
                }
            }
            log("6: " + Thread.currentThread().toString())
            delay(1000L)
            log("7: " + Thread.currentThread().toString())
        }
    }

    private fun dispatcherIO(){
        viewModelScope.launch(Dispatchers.Default){
            log("1: " + Thread.currentThread().toString())
            delay(1000L)
            log("2: " + Thread.currentThread().toString())
            withContext(Dispatchers.IO){
                delay(1000L)
                log("3: " + Thread.currentThread().toString())
            }
            log("4: " + Thread.currentThread().toString())
            delay(1000L)
            log("5: " + Thread.currentThread().toString())
        }
    }

    private fun dispatcherDefault(){
        viewModelScope.launch(Dispatchers.Default){
            launch{
                repeat(10){
                    delay(1000L)
                    log("1-$it" + Thread.currentThread().toString())
                }
            }
            launch{
                repeat(10){
                    delay(1000L)
                    log("2-$it" + Thread.currentThread().toString())
                }
            }
        }
    }
}