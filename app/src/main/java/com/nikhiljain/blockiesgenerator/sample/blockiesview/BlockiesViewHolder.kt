package com.nikhiljain.blockiesgenerator.sample.blockiesview

import androidx.core.content.ContextCompat
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
                    color = ContextCompat.getColor(itemView.context, blockiesViewsData.color),
                    bgColor = ContextCompat.getColor(itemView.context, blockiesViewsData.bgColor),
                    spotColor = ContextCompat.getColor(itemView.context, blockiesViewsData.spotColor)
                )
            )
            contactName.text = blockiesViewsData.name
            contactAddress.text = shortenAddressString(blockiesViewsData.address)
        }
    }
}