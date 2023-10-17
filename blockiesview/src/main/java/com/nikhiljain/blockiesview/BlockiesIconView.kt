package com.nikhiljain.blockiesview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import kotlin.math.floor
import kotlin.math.sqrt

/**
 * Custom view to display a Blockies icon
 */
class BlockiesIconView : View {
    private var blockiesData: BlockiesIconData
    private val colorPaint = Paint().also { it.style = Paint.Style.FILL }
    private val backgroundPaint = Paint().also {
        it.style = Paint.Style.FILL
        it.isAntiAlias = true
    }
    private val spotPaint: Paint = Paint().also {
        it.style = Paint.Style.FILL
    }
    private val backgroundRect = RectF()
    private var cornerRadius = DEFAULT_RADIUS
    private var shadowPaint = Paint().also {
        it.isAntiAlias = true
        it.isDither = true
    }
    private var brightPaint: Paint = Paint().also {
        it.isDither = true
    }
    private var hasShadow = false
    private var clipPath: Path? = null
    private var blocks = ArrayList<BlockDrawInfo>()
    private var blockiesDataSize: Int = BlockiesIconData.DEFAULT_SIZE

    constructor(context: Context?) : super(context) {
        blockiesData = BlockiesIconData("", blockiesDataSize)
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.BlockiesIdenticon, 0, 0)
        val seed: String
        try {
            cornerRadius = a.getDimension(R.styleable.BlockiesIdenticon_radius, DEFAULT_RADIUS)
            blockiesDataSize = a.getInt(
                R.styleable.BlockiesIdenticon_size, BlockiesIconData.DEFAULT_SIZE
            )
            seed = a.getString(R.styleable.BlockiesIdenticon_seed).orEmpty()
        } finally {
            a.recycle()
        }
        blockiesData = BlockiesIconData(seed, blockiesDataSize)
        init()
    }

    private fun init() {
        val colors = blockiesData.colors
        colorPaint.color = colors[0]
        backgroundPaint.color = colors[1]
        spotPaint.color = colors[2]
    }

    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        initBackgroundRect()
        configure()
    }

    private fun initBackgroundRect() {
        val right = width.toFloat()
        val bottom = height.toFloat()
        backgroundRect.set(
            /* left = */ 0f, /* top = */ 0f, /* right = */ right, /* bottom = */ bottom
        )
    }

    private fun configure() {
        val width = this.width
        val height = this.height
        configureClipPath(width, height)
        val data = blockiesData.imageData
        val blockSize = width / sqrt(data.size.toDouble())
        configureBlocks(data, blockSize, width)
        configureShadowIfNeeded(width, height, blockSize)
    }

    private fun configureShadowIfNeeded(width: Int, height: Int, blockSize: Double) {
        if (hasShadow) {
            val shadowColors = intArrayOf(
                Color.argb(200, 50, 50, 50),
                Color.argb(100, 0, 0, 0),
                Color.TRANSPARENT
            )
            val positions = floatArrayOf(0.20f, 0.35f, 1f)
            val shadowGradient = LinearGradient(
                (width / 2).toFloat(),
                height.toFloat(),
                (width / 2).toFloat(),
                (height - blockSize).toFloat(),
                shadowColors,
                positions,
                Shader.TileMode.CLAMP
            )
            shadowPaint.shader = shadowGradient
            val brightColors = intArrayOf(
                Color.argb(100, 255, 255, 255),
                Color.TRANSPARENT
            )
            val brightGradient = LinearGradient(
                (width / 2).toFloat(),
                0f,
                (width / 2).toFloat(),
                blockSize.toFloat(),
                brightColors,
                null,
                Shader.TileMode.CLAMP
            )
            brightPaint.shader = brightGradient
        }
    }

    private fun configureBlocks(data: IntArray, blockSize: Double, width: Int) {
        blocks = ArrayList()
        for (i in data.indices) {
            val x = blockSize * i % width
            val y = floor(blockSize * i / width) * blockSize
            val rect = RectF(
                x.toFloat(),
                y.toFloat(),
                (x + blockSize).toFloat(),
                (y + blockSize).toFloat()
            )
            if (data[i] == 2) {
                blocks.add(BlockDrawInfo(rect, spotPaint))
            } else if (data[i] == 1) {
                blocks.add(BlockDrawInfo(rect, colorPaint))
            }
        }
    }

    private fun configureClipPath(w: Int, h: Int) {
        clipPath = Path()
        val radius = 10.0f
        val padding = radius / 2
        clipPath?.addRoundRect(
            RectF(padding, padding, w - padding, h - padding),
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        clipPath?.let { canvas.clipPath(it) }
        canvas.drawRect(backgroundRect, backgroundPaint)
        blocks.forEach { canvas.drawRect(it.rect, it.paint) }
        if (hasShadow) {
            canvas.drawRect(backgroundRect, shadowPaint)
            canvas.drawRect(backgroundRect, brightPaint)
        }
    }

    fun setSeed(address: String) {
        this.setSeed(address, blockiesDataSize)
        init()
        invalidate()
    }

    fun setSeed(seed: String, size: Int) {
        blockiesData = BlockiesIconData(seed, size)
        init()
        invalidate()
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }

    private data class BlockDrawInfo(
        val rect: RectF,
        val paint: Paint
    )

    companion object {
        private const val DEFAULT_RADIUS = 0f
    }
}