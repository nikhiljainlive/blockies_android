package com.nikhiljain.blockiesglide.glidemodules

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.signature.ObjectKey
import com.nikhiljain.blockiesglide.entity.BlockiesIconData

class BlockiesModelLoader : ModelLoader<BlockiesIconData, BlockiesIconData> {

    override fun buildLoadData(
        model: BlockiesIconData,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<BlockiesIconData> {
        return ModelLoader.LoadData(ObjectKey(model), BlockiesDataFetcher(model))
    }

    override fun handles(model: BlockiesIconData): Boolean {
        return true
    }

    class Factory : ModelLoaderFactory<BlockiesIconData, BlockiesIconData> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<BlockiesIconData, BlockiesIconData> =
            BlockiesModelLoader()

        override fun teardown() {
            // Do nothing.
        }
    }
}
