package com.whistle.corecomponents

import android.content.Intent
import android.util.SparseArray
import android.view.MenuItem
import androidx.core.util.forEach
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setUpWithNavController(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent,
    onNavMenuClickListener: ((MenuItem) -> Unit)? = null
): LiveData<NavController> {
    val graphIdToTagMap = SparseArray<String>()
    val selectedNavController = MutableLiveData<NavController>()
    var firstFragmentGraphId = 0

    navGraphIds.forEachIndexed { index, id ->
        val fragmentTag = getFragmentTag(index)
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            id,
            containerId
        )

        val graphId = navHostFragment.navController.graph.id

        if(index == 0) {
            firstFragmentGraphId = graphId
        }

        graphIdToTagMap.put(graphId, fragmentTag)

        if(this.selectedItemId == graphId) {
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
        } else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    var selectedItemTag = graphIdToTagMap[this.selectedItemId]
    val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
    var isOnFirstFragment = selectedItemTag == firstFragmentTag

    setOnItemSelectedListener {
        onNavMenuClickListener?.invoke(it)
        if(fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedItemTag = graphIdToTagMap[it.itemId]
            if(selectedItemTag != newlySelectedItemTag) {
                // Quăng fragment đang hiện vào stack
                fragmentManager.popBackStack(firstFragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                // Lấy ra fragment có tag ứng với menu id chọn
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag) as NavHostFragment

                if(firstFragmentTag != newlySelectedItemTag) {
                    fragmentManager.beginTransaction()
                        .setCustomAnimations(
                            androidx.navigation.ui.R.anim.nav_default_enter_anim,
                            androidx.navigation.ui.R.anim.nav_default_exit_anim,
                            androidx.navigation.ui.R.anim.nav_default_pop_enter_anim,
                            androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
                        )
                        .show(selectedFragment)
                        .setPrimaryNavigationFragment(selectedFragment)
                        .apply {
                            graphIdToTagMap.forEach { _, fragmentTagItem ->
                                if(fragmentTagItem != newlySelectedItemTag) {
                                    hide(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                                }
                            }
                        }
                        .addToBackStack(firstFragmentTag)
                        .setReorderingAllowed(true)
                        .commit()
                }
                selectedItemTag = newlySelectedItemTag
                isOnFirstFragment = selectedItemTag == firstFragmentTag
                selectedNavController.value = selectedFragment.navController
                true
            } else {
                false
            }
        }
    }

    setupItemReselected(graphIdToTagMap, fragmentManager)

    setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    fragmentManager.addOnBackStackChangedListener {
        if(!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
            this.selectedItemId = firstFragmentGraphId
        }

        selectedNavController.value?.let {
            if(it.currentDestination == null) {
                it.navigate(it.graph.id)
            }
        }
    }
    return selectedNavController
}

private fun getFragmentTag(index: Int) = "bottomNavigation#$index"

private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {
    (fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?)?.let { return it }

    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.beginTransaction()
        .add(containerId, navHostFragment, fragmentTag)
        .commitNow()

    return navHostFragment
}

private fun BottomNavigationView.setupDeepLinks(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
) {
    navGraphIds.forEachIndexed { index, id ->
        val fragmentTag = getFragmentTag(index)

        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            id,
            containerId
        )

        if(navHostFragment.navController.handleDeepLink(intent) && selectedItemId != navHostFragment.navController.graph.id) {
            this.selectedItemId = navHostFragment.navController.graph.id
        }
    }
}

private fun BottomNavigationView.setupItemReselected(
    graphIdToTagMap: SparseArray<String>,
    fragmentManager: FragmentManager
) {
    setOnNavigationItemReselectedListener {
        val newlySelectedItemTag = graphIdToTagMap[it.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag) as NavHostFragment
        val navController = selectedFragment.navController

        navController.popBackStack(navController.graph.startDestinationId, false)
    }
}

private fun detachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {
    fragmentManager.beginTransaction().hide(navHostFragment).commitNow()
}

private fun attachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {
    fragmentManager.beginTransaction().show(navHostFragment).apply {
        if(isPrimaryNavFragment) {
            setPrimaryNavigationFragment(navHostFragment)
        }
    }.commitNow()
}

private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for(index in 0 until backStackCount) {
        if(getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}