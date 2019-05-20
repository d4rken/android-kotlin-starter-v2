package eu.darken.androidkotlinstarter.main.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import eu.darken.androidkotlinstarter.R
import eu.darken.androidkotlinstarter.main.ui.fragment.ExampleFragment
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BaseAppTheme_NoActionBar)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        viewModel.state.observe(this, Observer { if (it.ready) showExampleFragment() })

        viewModel.onGo()
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
        if (fragment == null) fragment = ExampleFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commitAllowingStateLoss()
    }
}
