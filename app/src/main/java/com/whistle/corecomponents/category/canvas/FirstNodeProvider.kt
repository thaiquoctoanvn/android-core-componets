package com.whistle.corecomponents.category.canvas

import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.whistle.corecomponents.R

class FirstNodeProvider(
    override val itemViewType: Int = 1,
    override val layoutId: Int
    ) : BaseNodeProvider() {
    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        with((item as FirstNode)) {
            helper.setText(R.id.tvAuthorFirstNode, commentData.authorName)
            helper.setText(R.id.tvCommentContentFirstNode, commentData.commentContent)
        }
    }

}