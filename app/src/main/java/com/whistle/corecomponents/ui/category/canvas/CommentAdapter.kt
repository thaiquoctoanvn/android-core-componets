package com.whistle.corecomponents.ui.category.canvas

import android.view.View
import com.chad.library.adapter.base.BaseNodeAdapter
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.whistle.corecomponents.R

class CommentAdapter : BaseNodeAdapter() {

    private val treeLevelInfo = mutableMapOf<Int, Int>()

    init {
        addNodeProvider(FirstNodeProvider(layoutId = R.layout.item_first_node_comment))
        addNodeProvider(SecondNodeProvider(layoutId = R.layout.item_second_node_comment))
    }

    override fun getItemType(data: List<BaseNode>, position: Int): Int {
        return when (data[position]) {
            is FirstNode -> 1
            is SecondNode -> 2
            else -> -1
        }
    }

    override fun convert(holder: BaseViewHolder, item: BaseNode) {
        super.convert(holder, item)
        when (item) {
            is FirstNode -> {
                if (data.indexOf(item) == data.size - 1) holder.getView<View>(R.id.vVerticalRootNode).visibility =
                    View.GONE
            }
            is SecondNode -> {
//                val parentId = item.commentData.belongToId
//                val parentNode =
//                    (data as MutableList<FirstNode>).find { it.commentData.id == parentId }
//                if (treeLevelInfo[parentId] == parentNode?.childNode?.size?.minus(1)) holder.getView<View>(
//                    R.id.vSecondVerticalLine
//                ).visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val a = 0
    }

    fun setTreeLevelInfo(map: MutableMap<Int, Int>) {
        treeLevelInfo.putAll(map)
    }
}