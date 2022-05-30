package com.whistle.corecomponents.util

import com.whistle.corecomponents.data.ComponentModel

fun provideComponentModel() = mutableListOf(
    ComponentModel(id = 1, name = "Work manager"),
    ComponentModel(id = 2, name = "Alarm manager"),
    ComponentModel(id = 3, name = "Web view"),
)