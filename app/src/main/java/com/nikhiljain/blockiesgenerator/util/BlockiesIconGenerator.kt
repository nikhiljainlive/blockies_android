package com.nikhiljain.blockiesgenerator.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import java.util.Locale
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

/**
 * @author Nikhil Jain
 *
 * @constructor Initializes this instance of [BlockyIconGenerator] with the given values or default values.
 *
 * @param seed The seed to be used for this Blockies icon.
 * @param size The number of blocks per side for this image. Defaults to 8.
 * @param color The foreground color. Defaults to color generated for seed.
 * @param bgColor The background color. Defaults to color generated for seed.
 * @param spotColor A color which forms mouths and eyes. Defaults to color generated for seed.
 */
class BlockiesIconGenerator(
    seed: String,
    size: Int,
    @ColorInt private var color: Int = 0,
    @ColorInt private var bgColor: Int = 0,
    @ColorInt private var spotColor: Int = 0,
) {
    // The random number is implementation of the Xorshift PRNG
    private val randSeed: IntArray = intArrayOf(0, 0, 0, 0) // Xorshift: [x, y, z, w] 32 bit values
    private val colorPaint = Paint().also { it.style = Paint.Style.FILL }
    private val backgroundPaint = Paint().also {
        it.style = Paint.Style.FILL
        it.isAntiAlias = true
    }
    private val spotPaint: Paint = Paint().also {
        it.style = Paint.Style.FILL
    }
    private val backgroundRect = RectF()
    private val clipPath = Path()
    private var imageData: IntArray = intArrayOf()

    init {
        seedRand(seed.lowercase(Locale.getDefault()))
        initialize(size)
    }

    private fun initialize(size: Int) {
        // create colors after seedRandom function
        if (color == 0) {
            color = createColor()
        }
        if (bgColor == 0) {
            bgColor = createColor()
        }
        if (spotColor == 0) {
            spotColor = createColor()
        }
        colorPaint.color = color
        backgroundPaint.color = bgColor
        spotPaint.color = spotColor
        imageData = createImageData(size)
    }

    private fun seedRand(seed: String): IntArray {
        for (i in seed.indices) {
            randSeed[i % 4] = (randSeed[i % 4] shl 5) - randSeed[i % 4] + seed[i].code
        }
        return randSeed
    }

    private fun rand(): Double {
        // based on Java's String.hashCode(), expanded to 4 32bit values
        val t = randSeed[0] xor (randSeed[0] shl 11)
        randSeed[0] = randSeed[1]
        randSeed[1] = randSeed[2]
        randSeed[2] = randSeed[3]
        randSeed[3] = randSeed[3] xor (randSeed[3] shr 19) xor t xor (t shr 8)
        val num = randSeed[3].toDouble()
        val den = (1 shl 31).toDouble()
        return abs(num / den)
    }

    private fun createColor(): Int {
        //saturation is the whole color spectrum
        val h = floor(rand() * 360).toFloat()
        //saturation goes from 40 to 100, it avoids greyish colors
        val s = (rand() * 60 + 40).toFloat() / 100
        //lightness can be anything from 0 to 100, but probabilities are a bell curve around 50%
        val l = ((rand() + rand() + rand() + rand()) * 25).toFloat() / 100
        return ColorUtils.HSLToColor(floatArrayOf(h, s, l))
    }

    private fun createImageData(size: Int): IntArray {
        // Only support square icons for now
        val width = size
        val height = width

        val dataWidth = ceil(width / 2.0).toInt()
        val mirrorWidth = width - dataWidth
        val data = ArrayList<Int>()
        for (y in 0 until height) {
            val row = ArrayList<Int>()
            for (x in 0 until dataWidth) {
                // this makes foreground and background color to have a 43% (1/2.3) probability
                // spot color has 13% chance
                val r = rand() * 2.3
                val d = floor(r)
                val add = d.toInt()
                row.add(add)
            }
            val r: MutableList<Int> = ArrayList()
            for (i in 0 until mirrorWidth) {
                r.add(row[i])
            }
            r.reverse()
            row.addAll(r)
            data.addAll(row)
        }
        return toIntArray(data)
    }

    private fun toIntArray(list: ArrayList<Int>): IntArray {
        val ret = IntArray(list.size)
        var i = 0
        for (e in list) ret[i++] = e
        return ret
    }

    /**
     * Generates blockies icon with given width, height and corner radius
     * @param width width for icon
     * @param height height for icon
     * @param cornerRadius corner radius for cornered icon. Defaults to 0.
     * */
    fun generateBlockiesIcon(width: Int, height: Int, cornerRadius: Float = 0f): Bitmap {
        if (width <= 0) {
            throw IllegalArgumentException("width must be > 0")
        }
        if (height <= 0) {
            throw IllegalArgumentException("height must be > 0")
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        // clip path for corner radius
        drawClipPath(width, height, cornerRadius, canvas)
        drawBackgroundRect(canvas)
        drawBlocks(width, canvas)
        return bitmap
    }

    private fun drawBlocks(width: Int, canvas: Canvas) {
        val blockSize = width / sqrt(imageData.size.toDouble())
        for (i in imageData.indices) {
            val x = blockSize * i % width
            val y = floor(blockSize * i / width) * blockSize
            val rect = RectF(
                x.toFloat(),
                y.toFloat(),
                (x + blockSize).toFloat(),
                (y + blockSize).toFloat()
            )
            // if data is 2, choose spot color, if 1 choose foreground
            if (imageData[i] == 2) {
                canvas.drawRect(rect, spotPaint)
            } else if (imageData[i] == 1) {
                canvas.drawRect(rect, colorPaint)
            }
        }
    }

    private fun drawBackgroundRect(canvas: Canvas) {
        backgroundRect.set(
            /* left = */ 0f,
            /* top = */ 0f,
            /* right = */ canvas.width.toFloat(),
            /* bottom = */ canvas.height.toFloat()
        )
        canvas.drawRect(backgroundRect, backgroundPaint)
    }

    private fun drawClipPath(
        width: Int,
        height: Int,
        cornerRadius: Float,
        canvas: Canvas
    ) {
        val radius = 10.0f
        val padding = radius / 2
        clipPath.addRoundRect(
            RectF(padding, padding, width - padding, height - padding),
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )
        canvas.clipPath(clipPath)
    }
}