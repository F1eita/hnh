package com.example.lesson_5_zhuravleva

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.properties.Delegates


class ColumnChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.ColumnChartStyle,
    defStyleRes: Int = R.style.DefaultColumnChartStyle
): View(context, attrs, defStyleAttr, defStyleRes) {

    var dataList: Map<String, Int> = emptyMap()
        set(value){
            field = value
            invalidate()
        }

    //Объявляем переменные для атрибутов
    private var textColor by Delegates.notNull<Int>()
    private var columnColor by Delegates.notNull<Int>()
    //Объявляем кисть
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    //Ширина одной ячейки, где располагается столбец, текст даты и текст процента
    private var widthOfOneColumnCell by Delegates.notNull<Float>()
    //Ширина столбика
    private var widthOfColumn by Delegates.notNull<Float>()
    //Отступ от начала ячейки для расположения столбца
    private var indentWidth by Delegates.notNull<Float>()
    //Маскимальная высота колонки(=100%)
    private var maxHeightOfColumn by Delegates.notNull<Float>()
    //Высота одной единицы столбца(=1%)
    private var heightOfOnePercentOfColumn by Delegates.notNull<Float>()

    private var currentColumnHeightPercent = 1f

    private val animation = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1000
        addUpdateListener {
            currentColumnHeightPercent = (it.animatedValue as Float)
            invalidate()
        }
    }

    private val gestureDetector = GestureDetector(context,
        object : GestureDetector.OnGestureListener {
            override fun onDown(p0: MotionEvent): Boolean {
                return false
            }

            override fun onShowPress(p0: MotionEvent) {

            }

            override fun onSingleTapUp(p0: MotionEvent): Boolean {
                return false
            }

            override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
                return false
            }

            override fun onLongPress(p0: MotionEvent) {
                startMyAnimation()
            }

            override fun onFling(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
                return false
            }
        })

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when {
            gestureDetector.onTouchEvent(event) -> true
            event.action == MotionEvent.ACTION_UP -> {
                startMyAnimation()
                true
            }

            else -> false
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (dataList.isNotEmpty()){
            calculateParams(if (dataList.size < 10) dataList.size else 9)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val layoutWidth = MeasureSpec.getSize(widthMeasureSpec)
        val layoutHeight = (layoutWidth*180)/360
        setMeasuredDimension(layoutWidth, layoutHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(backgroundTintList?.defaultColor ?: BACKGROUND_DEF_COLOR)
        var i = 0
        for ((date, value) in dataList){
            if (i == 9) break
            drawColumn(canvas, date to value, i)
            i++
        }
    }

    /** Отрисовка одной ячейки с колонкой и текстами*/
    private fun drawColumn(canvas: Canvas, data: Pair<String, Int>, i: Int){
        paint.color = columnColor
        val radius = widthOfColumn/2
        //Расчёт ширины, занимаемой уже отрисованными колонками
        val currentWidth = i*widthOfOneColumnCell
        //Расчёт координат столбика
        val left = currentWidth+indentWidth
        val bottom = 0.85f*height
        val top = bottom - heightOfOnePercentOfColumn*(data.second)*currentColumnHeightPercent
        val right = currentWidth+indentWidth+widthOfColumn
        canvas.drawRoundRect(left, top, right, bottom, radius, radius, paint)
        //Расчёт координат текста значения
        val valueX = currentWidth + (widthOfOneColumnCell/2) - paint
            .measureText(data.second.toString())/2
        val valueY = top - 0.05f*height
        canvas.drawText(data.second.toString(), valueX, valueY, paint)
        //Расчёт координат даты
        paint.color = textColor
        val dateX = currentWidth + (widthOfOneColumnCell/2) - paint
            .measureText(data.first)/2
        val dateY = bottom + 0.05f*height + paint.textSize
        canvas.drawText(data.first, dateX, dateY, paint)
    }

    /** Расчёт основных параметров, являющихся общими для всех колонок */
    private fun calculateParams(columnCount: Int){
        // Ширина ячейки для одной колонки зависит от количества колонок
        widthOfOneColumnCell= (width/columnCount).toFloat()
        // Ширина колонки будет равна 1/6 от ячейки, в которой она находится. Ширина между колонками
        // равна по размеру ширине пяти колонок, по этому отступ = 2.5 колонкам
        widthOfColumn = (widthOfOneColumnCell/6)
        indentWidth = (widthOfColumn*5)/2
        // Высчитываем высоту столбиков равных 1% и 100%
        maxHeightOfColumn = height*0.7f
        heightOfOnePercentOfColumn = maxHeightOfColumn/100
        // Высчитаем размер текста для значений и дат
        paint.textSize = height * 0.06f
    }

    fun startMyAnimation(){
        animation.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animation.cancel()
    }

    init{
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColumnChart,
            defStyleAttr, defStyleRes)
        textColor = typedArray.getColor(R.styleable.ColumnChart_textColor,
            TEXT_DEF_COLOR)
        columnColor = typedArray.getColor(R.styleable.ColumnChart_columnColor,
            COLUMN_DEF_COLOR)
        typedArray.recycle()
    }

    companion object{
        const val BACKGROUND_DEF_COLOR = Color.WHITE
        const val TEXT_DEF_COLOR = Color.GRAY
        const val COLUMN_DEF_COLOR = Color.GREEN
    }

}