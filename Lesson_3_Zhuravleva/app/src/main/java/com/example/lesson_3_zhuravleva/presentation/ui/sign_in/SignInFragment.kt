package com.example.lesson_3_zhuravleva.presentation.ui.sign_in

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseStates
import com.example.lesson_3_zhuravleva.databinding.FragmentSignInBinding
import com.example.lesson_3_zhuravleva.presentation.ui.exception.getError
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        SignInViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObserver()
        binding.apply {
            edtLogin.addTextChangedListener {
                textBoxLogin.isErrorEnabled = false
            }
            edtPassword.addTextChangedListener {
                textBoxPassword.isErrorEnabled = false
            }
            btnSignIn.setOnClickListener {
                if (isLoginCorrect() and isPasswordCorrect()) {
                    viewModel.login(edtLogin.text.toString(), edtPassword.text.toString())
                }
            }
            edtPassword.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    if (isLoginCorrect() and isPasswordCorrect()) {
                        viewModel.login(edtLogin.text.toString(), edtPassword.text.toString())
                    }
                    true
                } else {
                    false

                }
            }

        }
    }

    private fun isLoginCorrect(): Boolean{
        val text = binding.edtLogin.text.toString()
        binding.textBoxLogin.apply{
            if (Patterns.EMAIL_ADDRESS.matcher(text).matches().not()){
                isErrorEnabled = true
                error = getString(R.string.error_input_msg)
                return false
            }
            else{
                isErrorEnabled = false
                return true
            }
        }
    }

    private fun isPasswordCorrect(): Boolean{
        val text = binding.edtPassword.text.toString()
        binding.textBoxPassword.apply{
            if (text == ""){
                isErrorEnabled = true
                error = getString(R.string.error_input_msg)
                return false
            }
            else{
                isErrorEnabled = false
                return true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addObserver(){
        viewModel.signInLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseStates.Success -> {
                    findNavController().navigate(R.id.catalogFragment)
                }

                is ResponseStates.Failure -> {
                    binding.btnSignIn.isLoading = false
                    val snackbar = Snackbar.make(
                        requireView(), result.e.getError() ?: "",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setBackgroundTint(resources.getColor(R.color.error_red))
                    snackbar.show()
                }

                is ResponseStates.Loading -> {
                    binding.btnSignIn.isLoading = true
                }
            }
        }
    }
}
