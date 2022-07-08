package com.whistle.corecomponents.ui.category.canvas

import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.whistle.corecomponents.R

class SecondNodeProvider(
    override val itemViewType: Int = 2,
    override val layoutId: Int
    ) : BaseNodeProvider() {
    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        with((item as SecondNode)) {
            helper.setText(R.id.tvAuthorSecondNode, commentData.authorName)
            helper.setText(R.id.tvCommentContentSecondNode, commentData.commentContent)
        }
    }
}