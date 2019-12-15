package eu.darken.androidkotlinstarter.main.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import butterknife.ButterKnife
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import eu.darken.androidkotlinstarter.R
import eu.darken.androidkotlinstarter.common.dagger.AutoInject
import eu.darken.androidkotlinstarter.common.navigation.isGraphSet
import eu.darken.androidkotlinstarter.common.observe2
import eu.darken.androidkotlinstarter.common.smart.SmartActivity
import eu.darken.androidkotlinstarter.common.vdc.VDCSource
import eu.darken.androidkotlinstarter.common.vdc.vdcs
import javax.inject.Inject


class MainActivity : SmartActivity(), HasSupportFragmentInjector, AutoInject {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> = dispatchingAndroidInjector

    @Inject lateinit var vdcSource: VDCSource.Factory
    private val vdc: MainActivityVDC by vdcs { vdcSource }


    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BaseAppTheme)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)

        vdc.state.observe2(this) {
            if (it.ready && !navController.isGraphSet()) {
                val graph = navController.navInflater.inflate(R.navigation.main)

                navController.setGraph(graph, bundleOf("exampleArgument" to "hello"))
                setupActionBarWithNavController(navController)
            }
        }

        vdc.onGo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> {
            // NOOP
            true
        }
        else -> NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }
}
