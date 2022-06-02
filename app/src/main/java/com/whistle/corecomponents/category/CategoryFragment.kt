package com.whistle.corecomponents.category

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.whistle.corecomponents.BaseFragment
import com.whistle.corecomponents.R
import com.whistle.corecomponents.databinding.FragmentCategoryBinding
import com.whistle.corecomponents.util.NavDes
import com.whistle.corecomponents.util.provideComponentModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val componentList =provideComponentModel()
        val componentCategoryAdapter = ComponentCategoryAdapter(R.layout.item_component, componentList)

        binding.rvCategory.apply {
            adapter = componentCategoryAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
        componentCategoryAdapter.setOnItemClickListener {  adapter, view, position ->
            findNavController().navigate(getNavActionByDestination(componentList[position].destination))
        }
    }

    private fun getNavActionByDestination(des: String) = when (des) {
        NavDes.WORK_MANAGER_SAMPLE.name -> R.id.action_categoryFragment_to_workManagerFragment
        NavDes.IMAGE_LABELING_SAMPLE.name -> 0
        NavDes.TREE_NODE_RECYCLER_VIEW_SAMPLE.name -> R.id.action_categoryFragment_to_treeNodeRecyclerViewFragment
        else -> -1
    }
}