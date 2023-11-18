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
import kotlin.math.min
import kotlin.math.sqrt

/**
 * @author Nikhil Jain
 * <br>
 * [BlockiesIconDecoder] class to generate blockies bitmap icon
 */
class BlockiesIconDecoder(
    private var context: Context, private val bitmapPool: BitmapPool
) : ResourceDecoder<BlockiesIconData, BitmapDrawable> {

    override fun handles(source: BlockiesIconData, options: Options): Boolean {
        return true
    }

    override fun decode(
        source: BlockiesIconData,
        width: Int,
        height: Int,
        options: Options
    ): Resource<BitmapDrawable>? {
        val bitmap = bitmapPool.get(width, height, Bitmap.Config.ARGB_8888).also {
            it.drawBlockiesIcon(source, width, height)
        }
        val bitmapResource = BitmapResource.obtain(bitmap, bitmapPool)
        return LazyBitmapDrawableResource.obtain(context.resources, bitmapResource)
    }

    private fun Bitmap.drawBlockiesIcon(
        blockiesData: BlockiesIconData,
        bitmapWidth: Int,
        bitmapHeight: Int
    ): Bitmap {
        val canvas = Canvas(this)
        drawBackgroundRect(canvas, blockiesData, bitmapWidth, bitmapHeight)
        drawBlocks(canvas, blockiesData, bitmapWidth, bitmapHeight)
        return this
    }

    private fun drawBackgroundRect(
        canvas: Canvas,
        blockiesData: BlockiesIconData,
        bitmapWidth: Int,
        bitmapHeight: Int
    ) {
        val backgroundRect = RectF().also {
            it.set(
                /* left = */ 0f,
                /* top = */ 0f,
                /* right = */ bitmapWidth.toFloat(),
                /* bottom = */ bitmapHeight.toFloat()
            )
        }
        val backgroundPaint = Paint().also {
            it.style = Paint.Style.FILL
            it.isAntiAlias = true
            it.color = blockiesData.bgColor
        }
        canvas.drawRect(backgroundRect, backgroundPaint)
    }

    private fun drawBlocks(
        canvas: Canvas,
        blockiesData: BlockiesIconData,
        bitmapWidth: Int,
        bitmapHeight: Int
    ) {
        val bitmapSize = min(bitmapWidth, bitmapHeight)
        val imageData = blockiesData.imageData
        val blockSize = bitmapSize / sqrt(imageData.size.toDouble())
        val colorPaint = Paint().also {
            it.style = Paint.Style.FILL
            it.color = blockiesData.color
        }
        val spotPaint: Paint = Paint().also {
            it.style = Paint.Style.FILL
            it.color = blockiesData.spotColor
        }
        for (i in imageData.indices) {
            val x = blockSize * i % bitmapSize
            val y = floor(blockSize * i / bitmapSize) * blockSize
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