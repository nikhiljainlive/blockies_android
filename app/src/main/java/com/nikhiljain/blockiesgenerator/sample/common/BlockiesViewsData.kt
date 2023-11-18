package com.nikhiljain.blockiesgenerator.sample.common

import androidx.annotation.ColorRes

data class BlockiesViewsData(
    val name: String,
    val address: String,
    @ColorRes val color: Int = 0,
    @ColorRes val bgColor: Int = 0,
    @ColorRes val spotColor: Int = 0
)