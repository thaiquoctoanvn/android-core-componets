package com.whistle.corecomponents.category

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.whistle.corecomponents.BaseFragment
import com.whistle.corecomponents.R
import com.whistle.corecomponents.databinding.FragmentCategoryBinding
import com.whistle.corecomponents.util.provideComponentModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val componentCategoryAdapter = ComponentCategoryAdapter(R.layout.item_component, provideComponentModel())
        binding.rvCategory.apply {
            adapter = componentCategoryAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        componentCategoryAdapter.setOnItemClickListener {  adapter, view, position ->
            findNavController().navigate(R.id.action_categoryFragment_to_workManagerFragment)
        }
    }
}