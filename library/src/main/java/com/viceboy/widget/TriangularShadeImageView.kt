package com.viceboy.widget

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.graphics.ColorUtils
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator

/**
 * Created by sumitjha on 09/20/2020.
 */
class TriangularShadeImageView : AppCompatImageView {

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val listOfColors = mutableListOf<Int>()

    private val mFirstTriangleVerticesList = mutableListOf<Float>()
    private val mSecondTriangleVerticesList = mutableListOf<Float>()
    private val mThirdTriangleVerticesList = mutableListOf<Float>()

    private var mFirstTriangleLength = 0f
    private var mSecondTriangleLength = 0f
    private var mThirdTriangleLength = 0f

    private var mStartsFromFirstTriangle = 0
    private var mStartsFromSecondTriangle = 0
    private var mStartsFromThirdTriangle = 0

    private var mFirstTriangleAnimDelay = 0
    private var mSecondTriangleAnimDelay = 0
    private var mThirdTriangleAnimDelay = 0

    private var mFirstTriangleAnimDuration = 0
    private var mSecondTriangleAnimDuration = 0
    private var mThirdTriangleAnimDuration = 0


    private lateinit var mFirstTriangleDir: LayoutDir
    private lateinit var mSecondTriangleDir: LayoutDir
    private lateinit var mThirdTriangleDir: LayoutDir

    //Setup the overcast triangular color
    var shadeColor: Int = 0
        set(value) {
            if (field != value) {
                listOfColors.clear()
                var alpha = initialColorAlpha
                val tempLists = listOf(
                    mThirdTriangleVerticesList,
                    mSecondTriangleVerticesList,
                    mFirstTriangleVerticesList
                )
                tempLists.forEach { _ ->
                    listOfColors.add(
                        ColorUtils.setAlphaComponent(
                            value,
                            alpha
                        )
                    )
                    alpha += alphaIncrement
                }
                field = value
            }
        }


    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TriangularShadeImageView)
        retrieveAttributes(typedArray)
        typedArray.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mFirstTriangleVerticesList.addAll(
            createListOfVertices(
                mFirstTriangleDir,
                mFirstTriangleLength
            )
        )
        mSecondTriangleVerticesList.addAll(
            createListOfVertices(
                mSecondTriangleDir,
                mSecondTriangleLength
            )
        )
        mThirdTriangleVerticesList.addAll(
            createListOfVertices(
                mThirdTriangleDir,
                mThirdTriangleLength
            )
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (width == 0 && height == 0) return

        if (layerType != LAYER_TYPE_HARDWARE)
            setLayerType(LAYER_TYPE_HARDWARE, null)

        drawTriangle(canvas, paint.apply { color = listOfColors[0] }, mFirstTriangleVerticesList)

        drawTriangle(canvas, paint.apply { color = listOfColors[1] }, mSecondTriangleVerticesList)

        drawTriangle(canvas, paint.apply { color = listOfColors[2] }, mThirdTriangleVerticesList)
    }

    override fun onDetachedFromWindow() {
        if (layerType == LAYER_TYPE_HARDWARE)
            setLayerType(LAYER_TYPE_SOFTWARE, null)

        super.onDetachedFromWindow()
    }

    /**
     * Init all the required attributes
     */
    @SuppressLint("ResourceAsColor")
    private fun retrieveAttributes(typedArray: TypedArray) {
        mFirstTriangleLength =
            typedArray.getDimension(R.styleable.TriangularShadeImageView_firstTriangleSize, 0f)
        mSecondTriangleLength =
            typedArray.getDimension(R.styleable.TriangularShadeImageView_secondTriangleSize, 0f)
        mThirdTriangleLength =
            typedArray.getDimension(R.styleable.TriangularShadeImageView_thirdTriangleSize, 0f)

        mStartsFromFirstTriangle =
            typedArray.getInt(R.styleable.TriangularShadeImageView_startPtForAnimFirstTriangle, 0)
        mStartsFromSecondTriangle =
            typedArray.getInt(R.styleable.TriangularShadeImageView_startPtForAnimSecondTriangle, 0)
        mStartsFromThirdTriangle =
            typedArray.getInt(R.styleable.TriangularShadeImageView_startPtForAnimThirdTriangle, 0)

        mFirstTriangleAnimDelay =
            typedArray.getInt(R.styleable.TriangularShadeImageView_firstTriangleAnimDelay, 0)
        mSecondTriangleAnimDelay =
            typedArray.getInt(R.styleable.TriangularShadeImageView_secondTriangleAnimDelay, 0)
        mThirdTriangleAnimDelay =
            typedArray.getInt(R.styleable.TriangularShadeImageView_thirdTriangleAnimDelay, 0)

        mFirstTriangleAnimDuration =
            typedArray.getInt(R.styleable.TriangularShadeImageView_firstTriangleAnimDuration, 0)
        mSecondTriangleAnimDuration =
            typedArray.getInt(R.styleable.TriangularShadeImageView_secondTriangleAnimDuration, 0)
        mThirdTriangleAnimDuration =
            typedArray.getInt(R.styleable.TriangularShadeImageView_thirdTriangleAnimDuration, 0)

        mFirstTriangleDir = typedArray.getEnum(
            R.styleable.TriangularShadeImageView_firstTriangleDrawDir,
            LayoutDir.TOP_LEFT
        )
        mSecondTriangleDir = typedArray.getEnum(
            R.styleable.TriangularShadeImageView_secondTriangleDrawDir,
            LayoutDir.TOP_LEFT
        )
        mThirdTriangleDir = typedArray.getEnum(
            R.styleable.TriangularShadeImageView_thirdTriangleDrawDir,
            LayoutDir.TOP_LEFT
        )

        shadeColor = typedArray.getColor(R.styleable.TriangularShadeImageView_shadeColor,android.R.color.holo_orange_light)
    }

    /**
     * Generate list of required vertices depending on the LayoutDir
     */
    private fun createListOfVertices(
        layoutDir: LayoutDir,
        triangleLength: Float
    ): List<Float> {
        return when (layoutDir) {
            LayoutDir.TOP_LEFT -> (
                    listOf(
                        initial, initial, initial,
                        triangleLength,
                        triangleLength, initial
                    )
                    )
            LayoutDir.TOP_RIGHT ->
                listOf(
                    width.toFloat(),
                    initial,
                    width.toFloat(),
                    triangleLength,
                    width-triangleLength,
                    initial
                )

            LayoutDir.BOTTOM_LEFT ->
                listOf(
                    initial, height.toFloat(), initial, height - triangleLength,
                    triangleLength, height.toFloat()
                )

            LayoutDir.BOTTOM_RIGHT ->
                listOf(
                    width.toFloat(),
                    height.toFloat(),
                    width.toFloat(),
                    height - triangleLength,
                    width - triangleLength,
                    height.toFloat()
                )
        }
    }

    /**
     * Draw the triangle depending on the vertices
     */
    private fun drawTriangle(canvas: Canvas?, paint: Paint, listOfVertices: MutableList<Float>) {
        val path = Path().apply {
            moveTo(listOfVertices[0], listOfVertices[1])
            lineTo(listOfVertices[2], listOfVertices[3])
            lineTo(listOfVertices[4], listOfVertices[5])
            close()
        }
        canvas?.drawPath(path, paint)
    }

    /**
     * Create a ValueAnimator to animate drawing of triangle
     * with [startRange] as [mStartsFromFirstTriangle],
     * [mStartsFromSecondTriangle] and [mStartsFromThirdTriangle]
     */
    private fun createAnimator(
        startRange: Int,
        startX1: Float,
        listOfVertices: MutableList<Float>,
        triangleDir: LayoutDir,
        delay: Int,
        duration: Int
    ) =
        ValueAnimator.ofInt(startRange, endWith).apply {

            this.interpolator = DecelerateInterpolator()
            this.startDelay = delay.toLong()
            this.duration = duration.toLong()

            addUpdateListener {
                updateList(it.animatedValue as Int, listOfVertices, startX1, triangleDir)
                invalidate()
            }
        }


    /**
     * Update the generated list of vertices
     * on ValueAnimator update
     */
    private fun updateList(
        fl: Int,
        listOfVertices: MutableList<Float>,
        startX1: Float,
        triangleDir: LayoutDir
    ) {
        val listOfVertex = createListOfVertices(triangleDir, startX1)
        val size = listOfVertices.size

        for (i in 0 until size) {
            listOfVertices.removeAt(i)
            listOfVertices.add(i, listOfVertex[i] + fl)
        }
    }

    /**
     * Animate the drawing of triangle
     */
    fun animateDraw() {
        AnimatorSet().apply {
            playTogether(
                createAnimator(
                    mStartsFromFirstTriangle,
                    mFirstTriangleLength,
                    mFirstTriangleVerticesList,
                    mFirstTriangleDir,
                    mFirstTriangleAnimDelay,
                    mFirstTriangleAnimDuration
                ),
                createAnimator(
                    mStartsFromSecondTriangle,
                    mSecondTriangleLength,
                    mSecondTriangleVerticesList,
                    mSecondTriangleDir,
                    mSecondTriangleAnimDelay,
                    mSecondTriangleAnimDuration
                ),
                createAnimator(
                    mStartsFromThirdTriangle,
                    mThirdTriangleLength,
                    mThirdTriangleVerticesList,
                    mThirdTriangleDir,
                    mThirdTriangleAnimDelay,
                    mThirdTriangleAnimDuration
                )
            )
        }.start()
    }

    /**
     * Using extension method [getEnum] from https://www.thetopsites.net/article/54341705.shtml
     */
    inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int, default: T) =
        getInt(index, -1).let {
            if (it >= 0) enumValues<T>()[it] else default
        }

    companion object {
        private const val initial = 0f

        private const val endWith = 0
        private const val alphaIncrement = 50
        private const val initialColorAlpha = 130

        /**
         * Enum class to decide where to draw the triangle
         */
        private enum class LayoutDir {
            TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
        }
    }
}