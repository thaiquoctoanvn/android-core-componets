package com.whistle.corecomponents.ui.category.canvas

import com.chad.library.adapter.base.entity.node.BaseNode

data class FirstNode(override val childNode: MutableList<BaseNode>?, val commentData: CommentModel) : BaseNode()