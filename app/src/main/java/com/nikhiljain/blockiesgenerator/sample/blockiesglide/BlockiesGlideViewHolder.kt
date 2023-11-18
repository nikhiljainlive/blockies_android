package com.nikhiljain.blockiesgenerator.sample.blockiesglide

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.nikhiljain.blockiesgenerator.R
import com.nikhiljain.blockiesgenerator.databinding.ListItemBlockiesGeneratorBinding
import com.nikhiljain.blockiesgenerator.sample.common.BaseBlockiesViewHolder
import com.nikhiljain.blockiesgenerator.sample.common.BlockiesViewsData
import com.nikhiljain.blockiesgenerator.sample.common.StringUtil
import com.nikhiljain.blockiesglide.entity.BlockiesIconData

class BlockiesGlideViewHolder(
    private val itemBinding: ListItemBlockiesGeneratorBinding
) : BaseBlockiesViewHolder(itemBinding.root) {
    override fun bind(blockiesViewsData: BlockiesViewsData) {
        itemBinding.apply {
            val blockiesData = BlockiesIconData(
                seed = blockiesViewsData.address,
                size = 10,
                scale = 10,
                color = itemView.context.getColor(blockiesViewsData.color),
                bgColor = itemView.context.getColor(blockiesViewsData.bgColor),
                spotColor = itemView.context.getColor(blockiesViewsData.spotColor)
            )
            Glide.with(contactIcon)
                .load(blockiesData)
                .placeholder(R.drawable.ic_launcher_foreground)
                .transform(RoundedCorners(90))
                .into(contactIcon)

            contactName.text = blockiesViewsData.name
            contactAddress.text = StringUtil.shortenAddressString(blockiesViewsData.address)
        }
    }
}