package eu.darken.androidkotlinstarter.main.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import eu.darken.androidkotlinstarter.R
import eu.darken.androidkotlinstarter.common.dagger.AutoInject
import eu.darken.androidkotlinstarter.common.dagger.VDCFactory
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragment
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, AutoInject {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var vdcFactory: VDCFactory.Factory

    private val vdc: MainActivityVDC by viewModels { vdcFactory.create(this, null) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BaseAppTheme_NoActionBar)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)

        vdc.state.observe(this, Observer { if (it.ready) showExampleFragment() })

        vdc.onGo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> = dispatchingAndroidInjector

    fun showExampleFragment() {
        var fragment = supportFragmentManager.findFragmentById(R.id.content_frame)
        if (fragment == null) {
            fragment = ExampleFragment.newInstance()
            fragment.arguments = Bundle().apply { putString("fragmentArg", UUID.randomUUID().toString()) }
        }
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commitAllowingStateLoss()
    }
}
