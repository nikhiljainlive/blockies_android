package com.nikhiljain.blockiesglide.glidemodules

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapResource
import com.bumptech.glide.load.resource.bitmap.LazyBitmapDrawableResource
import com.nikhiljain.blockiesglide.entity.BlockiesIconData
import kotlin.math.floor
import kotlin.math.sqrt

class BlockiesDecoder(private var context: Context, private val bitmapPool: BitmapPool) :
    ResourceDecoder<BlockiesIconData, BitmapDrawable> {

    override fun handles(source: BlockiesIconData, options: Options): Boolean {
        return true
    }

    override fun decode(
        source: BlockiesIconData,
        width: Int,
        height: Int,
        options: Options
    ): Resource<BitmapDrawable>? {
        val bitmap = generateBitmap(source, source.width, source.width, options)
        val bitmapResource = BitmapResource.obtain(bitmap, bitmapPool)
        return LazyBitmapDrawableResource.obtain(
            context.resources,
            bitmapPool,
            bitmapResource!!.get()
        )
    }

    @Suppress("UNUSED_PARAMETER")
    private fun generateBitmap(
        source: BlockiesIconData,
        width: Int,
        height: Int,
        options: Options?
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(source.width, source.width, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawBackgroundRect(canvas, source)
        drawBlocks(canvas, source)
        return bitmap//scaleBitmap(bitmap, width, height)
    }

    private fun drawBackgroundRect(canvas: Canvas, blockiesData: BlockiesIconData) {
        val backgroundRect = RectF().also {
            it.set(
                /* left = */ 0f,
                /* top = */ 0f,
                /* right = */ blockiesData.width.toFloat(),
                /* bottom = */ blockiesData.width.toFloat()
            )
        }
        val backgroundPaint = Paint().also {
            it.style = Paint.Style.FILL
            it.isAntiAlias = true
            it.color = blockiesData.bgColor
        }
        canvas.drawRect(backgroundRect, backgroundPaint)
    }

    private fun drawBlocks(canvas: Canvas, blockiesData: BlockiesIconData) {
        val width = blockiesData.width
        val imageData = blockiesData.imageData
        val blockSize = width / sqrt(imageData.size.toDouble())
        val colorPaint = Paint().also {
            it.style = Paint.Style.FILL
            it.color = blockiesData.color
        }
        val spotPaint: Paint = Paint().also {
            it.style = Paint.Style.FILL
            it.color = blockiesData.spotColor
        }
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
}

