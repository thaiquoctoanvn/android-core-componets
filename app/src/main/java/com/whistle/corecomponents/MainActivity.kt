package com.whistle.corecomponents

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.whistle.corecomponents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        val navGraphIds =
            listOf(R.navigation.nav_intro, R.navigation.nav_category, R.navigation.nav_profile)

        binding?.bnv?.setUpWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.fcv,
            intent = intent,
            onNavMenuClickListener = {
                setMainTitle(it.title.toString())
            }
        )
    }

    fun setMainTitle(title: String, isCategoryTab: Boolean = false) {
        if (isCategoryTab) {
            binding?.tvMainSubTitle?.apply {
                visibility = View.VISIBLE
                text = title
            }
        }
        else {
            binding?.tvMainTitle?.text = title
            binding?.tvMainSubTitle?.visibility = View.GONE
        }
    }
}