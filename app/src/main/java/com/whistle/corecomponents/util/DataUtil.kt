package com.whistle.corecomponents.util

import com.whistle.corecomponents.data.ComponentModel

fun provideComponentModel() = mutableListOf(
    ComponentModel(id = 1, name = "Work manager", destination = NavDes.WORK_MANAGER_SAMPLE.name),
    ComponentModel(id = 2, name = "Alarm manager", destination = NavDes.ALARM_MANAGER_SAMPLE.name),
    ComponentModel(id = 3, name = "Web view", destination = ""),
    ComponentModel(id = 4, name = "Tree node recycler view", destination = NavDes.TREE_NODE_RECYCLER_VIEW_SAMPLE.name),
    ComponentModel(id = 5, name = "Image labeling", destination = NavDes.IMAGE_LABELING_SAMPLE.name)
)