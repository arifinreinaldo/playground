package com.explore.playground.navigator

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import kotlinx.android.synthetic.main.activity_navigator.*

class NavigatorActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_navigator
    }

    override fun setInit() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.navFragment) as NavHostFragment? ?: return
        val controller = host.navController

        bottomNav?.setupWithNavController(controller)
    }
}
