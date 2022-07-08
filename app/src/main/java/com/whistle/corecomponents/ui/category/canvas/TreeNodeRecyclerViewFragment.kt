package com.whistle.corecomponents.ui.category.canvas

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.entity.node.BaseNode
import com.whistle.corecomponents.BaseFragment
import com.whistle.corecomponents.databinding.FragmentTreeNodeRecyclerViewBinding

class TreeNodeRecyclerViewFragment : BaseFragment<FragmentTreeNodeRecyclerViewBinding>(FragmentTreeNodeRecyclerViewBinding::inflate) {

    private val treeLevelInfo = mutableMapOf<Int, Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val commentAdapter = CommentAdapter().apply {
            setList(provideNodeList(provideCommentList()))
            setTreeLevelInfo(treeLevelInfo)
        }
        binding.rvComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun provideNodeList(comments: MutableList<CommentModel>): MutableList<BaseNode> {
        val baseNodeList = mutableListOf<BaseNode>()
        val firstNodes = comments.filter { it.belongToId == 0 }.map {
            FirstNode(childNode = mutableListOf(), commentData = it)
        }
        firstNodes.forEach { firstNode ->
            val secondNodes = mutableListOf<SecondNode>()
            comments.forEach { comment ->
                if (comment.belongToId == firstNode.commentData.id) {
                    secondNodes.add(SecondNode(commentData = comment))
                }
            }
            firstNode.childNode?.addAll(secondNodes)
            if (secondNodes.isNotEmpty()) treeLevelInfo[firstNode.commentData.id] = secondNodes.size
        }
        baseNodeList.addAll(firstNodes)
        return baseNodeList
    }

    private fun provideCommentList() = mutableListOf(
        CommentModel(1, "Jame", "Hi, this is level 1", 0),
        CommentModel(2, "Tony", "Hi, this is level 2", 1),
        CommentModel(3, "Luke", "Hi, this is level 2", 1),
        CommentModel(4, "Jame", "Hi, this is level 2", 0),
        CommentModel(5, "Jame", "Hi, this is level 1", 4)
    )
}

data class CommentModel(
    val id: Int,
    val authorName: String,
    val commentContent: String,
    val belongToId: Int = 0
    )