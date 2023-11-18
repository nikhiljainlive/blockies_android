package com.nikhiljain.blockiesglide.glidemodules

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.nikhiljain.blockiesglide.entity.BlockiesIconData

/**
 * @author Nikhil Jain
 * <br>
 * [BlockiesIconGlideModule] class to register Blockies Loader and Blockies Decoder class
 */
@GlideModule
class BlockiesIconGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.append(
            BlockiesIconData::class.java, BlockiesIconData::class.java,
            BlockiesIconModelLoader.Factory()
        )
        registry.append(
            BlockiesIconData::class.java,
            BitmapDrawable::class.java,
            BlockiesIconDecoder(context, glide.bitmapPool)
        )
    }
}