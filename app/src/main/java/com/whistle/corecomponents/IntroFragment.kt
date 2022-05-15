package com.whistle.corecomponents

import android.os.Bundle
import android.view.View
import com.whistle.corecomponents.databinding.FragmentIntroBinding

class IntroFragment : BaseFragment<FragmentIntroBinding>(FragmentIntroBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setMainTitle("Intro")
    }
}