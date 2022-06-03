package com.whistle.corecomponents.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.whistle.corecomponents.databinding.FragmentSimpleBinding

open class SimpleFragment : Fragment() {

    protected lateinit var binding: FragmentSimpleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSimpleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSampleComponent()
    }

    open fun setUpSampleComponent() {

    }
}