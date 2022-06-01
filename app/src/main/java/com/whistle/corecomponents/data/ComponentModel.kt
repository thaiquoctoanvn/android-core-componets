package com.whistle.corecomponents.data

import com.whistle.corecomponents.R

data class ComponentModel(
    val id: Int,
    val iconRes: Int = R.drawable.ic_baseline_album_24,
    val name: String,
    val description: String = ""
)
