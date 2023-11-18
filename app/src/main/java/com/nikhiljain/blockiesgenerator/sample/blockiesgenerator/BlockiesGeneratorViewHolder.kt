package com.nikhiljain.blockiesgenerator.sample.blockiesgenerator

import androidx.core.content.ContextCompat
import com.nikhiljain.blockiesgenerator.BlockiesIconGenerator
import com.nikhiljain.blockiesgenerator.R
import com.nikhiljain.blockiesgenerator.databinding.ListItemBlockiesGeneratorBinding
import com.nikhiljain.blockiesgenerator.sample.common.BaseBlockiesViewHolder
import com.nikhiljain.blockiesgenerator.sample.common.BlockiesViewsData
import com.nikhiljain.blockiesgenerator.sample.common.ImageHelper
import com.nikhiljain.blockiesgenerator.sample.common.StringUtil
import com.nikhiljain.blockiesgenerator.sample.common.dpToPx

class BlockiesGeneratorViewHolder(
    private val itemBinding: ListItemBlockiesGeneratorBinding
) : BaseBlockiesViewHolder(itemBinding.root) {
    override fun bind(blockiesViewsData: BlockiesViewsData) {
        itemBinding.apply {
            val iconGenerator = BlockiesIconGenerator(
                seed = blockiesViewsData.address,
                size = 10,
                scale = 10,
                color = ContextCompat.getColor(itemView.context, blockiesViewsData.color),
                bgColor = ContextCompat.getColor(itemView.context, blockiesViewsData.bgColor),
                spotColor = ContextCompat.getColor(itemView.context, blockiesViewsData.spotColor)
            )
            val context = itemView.context
            contactIcon.setImageBitmap(
                ImageHelper.getRoundedCornerBitmap(
                    iconGenerator.generateIconBitmap(),
                    22f.dpToPx(context).toInt(),
                    ContextCompat.getColor(context, R.color.grey_light)
                )
            )
            contactName.text = blockiesViewsData.name
            contactAddress.text = StringUtil.shortenAddressString(blockiesViewsData.address)
        }
    }
}