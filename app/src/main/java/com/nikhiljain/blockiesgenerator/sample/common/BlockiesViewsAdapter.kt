package com.nikhiljain.blockiesgenerator.sample.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikhiljain.blockiesgenerator.databinding.ListItemBlockiesGeneratorBinding
import com.nikhiljain.blockiesgenerator.databinding.ListItemBlockiesViewBinding
import com.nikhiljain.blockiesgenerator.sample.blockiesgenerator.BlockiesGeneratorViewHolder
import com.nikhiljain.blockiesgenerator.sample.blockiesglide.BlockiesGlideViewHolder
import com.nikhiljain.blockiesgenerator.sample.blockiesview.BlockiesViewHolder

class BlockiesViewsAdapter private constructor(
    private val blockiesData: List<BlockiesViewsData>,
    private val viewType: Int
) : RecyclerView.Adapter<BaseBlockiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBlockiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEWHOLDER_TYPE_BLOCKIES_VIEW -> BlockiesViewHolder(
                ListItemBlockiesViewBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            VIEWHOLDER_TYPE_BLOCKIES_GENERATOR -> BlockiesGeneratorViewHolder(
                ListItemBlockiesGeneratorBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            VIEWHOLDER_TYPE_BLOCKIES_GLIDE -> BlockiesGlideViewHolder(
                ListItemBlockiesGeneratorBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Unknown view type for ViewHolder")
        }
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: BaseBlockiesViewHolder, position: Int) {
        holder.bind(blockiesData[position % blockiesData.size])
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    companion object {
        private const val VIEWHOLDER_TYPE_BLOCKIES_VIEW = 1
        private const val VIEWHOLDER_TYPE_BLOCKIES_GENERATOR = 2
        private const val VIEWHOLDER_TYPE_BLOCKIES_GLIDE = 3

        fun getAdapterForBlockiesView(blockiesData: List<BlockiesViewsData>): BlockiesViewsAdapter {
            return BlockiesViewsAdapter(blockiesData, VIEWHOLDER_TYPE_BLOCKIES_VIEW)
        }

        fun getAdapterForBlockiesGenerator(blockiesData: List<BlockiesViewsData>): BlockiesViewsAdapter {
            return BlockiesViewsAdapter(blockiesData, VIEWHOLDER_TYPE_BLOCKIES_GENERATOR)
        }

        fun getAdapterForBlockiesGlide(blockiesData: List<BlockiesViewsData>): BlockiesViewsAdapter {
            return BlockiesViewsAdapter(blockiesData, VIEWHOLDER_TYPE_BLOCKIES_GLIDE)
        }
    }
}