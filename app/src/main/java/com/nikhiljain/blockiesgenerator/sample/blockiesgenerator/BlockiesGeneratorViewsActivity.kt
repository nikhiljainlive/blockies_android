package com.nikhiljain.blockiesgenerator.sample.blockiesgenerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhiljain.blockiesgenerator.databinding.ActivityBlockiesViewsBinding
import com.nikhiljain.blockiesgenerator.sample.common.BlockiesViewsAdapter
import com.nikhiljain.blockiesgenerator.sample.common.DataUtil.getBlockiesData

class BlockiesGeneratorViewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBlockiesViewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlockiesViewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpBindings()
    }

    private fun setUpBindings() {
        binding.toolbar.title = "Blockies Generator (Bitmap)"
        binding.recyclerView.adapter = BlockiesViewsAdapter.getAdapterForBlockiesGenerator(
            getBlockiesData()
        )
    }
}