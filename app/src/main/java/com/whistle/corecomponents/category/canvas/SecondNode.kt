package com.whistle.corecomponents.category.canvas

import com.chad.library.adapter.base.entity.node.BaseNode

data class SecondNode(override val childNode: MutableList<BaseNode>? = null, val commentData: CommentModel) : BaseNode()