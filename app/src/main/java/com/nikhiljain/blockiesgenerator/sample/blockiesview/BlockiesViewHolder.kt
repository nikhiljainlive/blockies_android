package com.nikhiljain.blockiesgenerator.sample.blockiesview

import com.nikhiljain.blockiesgenerator.databinding.ListItemBlockiesViewBinding
import com.nikhiljain.blockiesgenerator.sample.common.BaseBlockiesViewHolder
import com.nikhiljain.blockiesgenerator.sample.common.BlockiesViewsData
import com.nikhiljain.blockiesgenerator.sample.common.StringUtil.shortenAddressString
import com.nikhiljain.blockiesview.BlockiesIconData

class BlockiesViewHolder(
    private val itemBinding: ListItemBlockiesViewBinding
) : BaseBlockiesViewHolder(itemBinding.root) {
    override fun bind(blockiesViewsData: BlockiesViewsData) {
        itemBinding.apply {
            contactIcon.setBlockiesIconData(
                BlockiesIconData(
                    seed = blockiesViewsData.address,
                    size = 10,
                    color = itemView.context.getColor(blockiesViewsData.color),
                    bgColor = itemView.context.getColor(blockiesViewsData.bgColor),
                    spotColor = itemView.context.getColor(blockiesViewsData.spotColor)
                )
            )
            contactName.text = blockiesViewsData.name
            contactAddress.text = shortenAddressString(blockiesViewsData.address)
        }
    }
}