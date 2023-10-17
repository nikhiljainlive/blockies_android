package com.nikhiljain.blockiesgenerator.sample.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBlockiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(blockiesViewsData: BlockiesViewsData)
}