package com.nikhiljain.blockiesglide.glidemodules

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.nikhiljain.blockiesglide.entity.BlockiesIconData

@GlideModule
open class BlockiesGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.append(BlockiesIconData::class.java, BlockiesIconData::class.java, BlockiesModelLoader.Factory())
        registry.append(BlockiesIconData::class.java, BitmapDrawable::class.java, BlockiesDecoder(context, glide.bitmapPool))
    }
}