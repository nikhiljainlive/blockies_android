package com.nikhiljain.blockiesgenerator.sample

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import com.bumptech.glide.Glide
import com.nikhiljain.blockiesgenerator.BlockiesIconGenerator
import com.nikhiljain.blockiesgenerator.databinding.ActivityMainBinding
import com.nikhiljain.blockiesglide.entity.BlockiesIconData
import com.nikhiljain.blockiesview.BlockiesIconView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addresses = arrayOf(
            "0xfb6916095ca1df60bb79ce92ce3ea74c37c5d359",
            "0x554f8e6938004575bd89cbef417aea5c18140d92",
            "0xcc6294200fa6e6eb5c3034ed6b0b80401f5b0ceb",
            "0xbb9bc244d798123fde783fcc1c72d3bb8c189413",
            "0x6090a6e47849629b7245dfa1ca21d94cd15878ef",
            "0x314159265dd8dbb310642f98f50c066173c1259b"
        )
        binding.linearLayoutInner1.post {
            val size = binding.linearLayoutInner1.width / addresses.size
            addresses.forEach {
                val imageView = ImageView(this)
                imageView.layoutParams = ViewGroup.LayoutParams(size, size)
                imageView.updatePadding(
                    10, 0, 0, 0
                )
                val iconGenerator = BlockiesIconGenerator(seed = it, size = 10, scale = 10)
                val blockiesIconBitmap = iconGenerator.generateIconBitmap()
                imageView.setImageBitmap(blockiesIconBitmap)
                binding.linearLayoutInner1.addView(imageView)
            }
            binding.linearLayout.addView(
                TextView(this).also {
                    it.text = "Size 8"
                    it.setTextColor(Color.BLACK)
                }, 0
            )
        }
        binding.linearLayoutInner2.post {
            val size = binding.linearLayoutInner2.width / addresses.size
            addresses.forEach {
                val blockiesIdenticon = BlockiesIconView(this)
                blockiesIdenticon.layoutParams = ViewGroup.LayoutParams(size, size)
                blockiesIdenticon.updatePadding(
                    10, 10, 10, 10
                )
                blockiesIdenticon.setSeed(it, 10)
                blockiesIdenticon.setCornerRadius(0f)
                binding.linearLayoutInner2.addView(blockiesIdenticon)
            }
            binding.linearLayout.addView(
                TextView(this).also {
                    it.text = "Size 10"
                    it.setTextColor(Color.BLACK)
                }, 2
            )
        }
        Glide.with(binding.imageviewGlide)
            .load(
                BlockiesIconData(
                    seed = "0xfb6916095ca1df60bb79ce92ce3ea74c37c5d359",
                    size = 10,
                    color = ContextCompat.getColor(
                        this,
                        android.R.color.holo_green_light
                    ),
                    bgColor = ContextCompat.getColor(
                        this,
                        android.R.color.holo_blue_light
                    ),
                    spotColor = ContextCompat.getColor(
                        this,
                        android.R.color.holo_orange_dark
                    )
                )
            ).into(binding.imageviewGlide)
    }
}