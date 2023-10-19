package com.nikhiljain.blockiesglide.glidemodules

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.nikhiljain.blockiesglide.entity.BlockiesIconData

class BlockiesDataFetcher(private val blockiesData: BlockiesIconData) : DataFetcher<BlockiesIconData> {

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in BlockiesIconData>) {
        callback.onDataReady(blockiesData)
    }

    override fun cleanup() {
        // Do nothing.
    }

    override fun cancel() {
        // Do nothing.
    }

    override fun getDataClass(): Class<BlockiesIconData> = BlockiesIconData::class.java

    override fun getDataSource(): DataSource = DataSource.LOCAL
}