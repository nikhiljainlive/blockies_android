package com.nikhiljain.blockiesgenerator.sample.blockiesview

import com.nikhiljain.blockiesgenerator.databinding.ListItemBlockiesViewBinding
import com.nikhiljain.blockiesgenerator.sample.common.BlockiesViewsData
import com.nikhiljain.blockiesgenerator.sample.common.BaseBlockiesViewHolder
import com.nikhiljain.blockiesgenerator.sample.common.StringUtil.shortenAddressString

class BlockiesViewHolder(
    private val itemBinding: ListItemBlockiesViewBinding
) : BaseBlockiesViewHolder(itemBinding.root) {
    override fun bind(blockiesViewsData: BlockiesViewsData) {
        itemBinding.apply {
            contactIcon.setSeed(blockiesViewsData.address, 10)
            contactName.text = blockiesViewsData.name
            contactAddress.text = shortenAddressString(blockiesViewsData.address)
        }
    }
}