package com.nikhiljain.blockiesgenerator.sample.blockiesglide

import androidx.core.content.ContextCompat
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
                color = ContextCompat.getColor(itemView.context, blockiesViewsData.color),
                bgColor = ContextCompat.getColor(itemView.context, blockiesViewsData.bgColor),
                spotColor = ContextCompat.getColor(itemView.context, blockiesViewsData.spotColor)
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