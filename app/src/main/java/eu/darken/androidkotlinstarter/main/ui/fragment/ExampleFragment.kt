package eu.darken.androidkotlinstarter.main.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.view.clicks
import dagger.android.support.AndroidSupportInjection
import eu.darken.androidkotlinstarter.R
import eu.darken.androidkotlinstarter.common.smart.SmartFragment
import javax.inject.Inject


class ExampleFragment : SmartFragment() {
    companion object {
        fun newInstance(): Fragment = ExampleFragment()
    }

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.emoji_text) lateinit var emojiText: TextView
    @BindView(R.id.fab) lateinit var fab: FloatingActionButton

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ExampleFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.example_fragment, container, false)
        addUnbinder(ButterKnife.bind(this, layout))
        return layout
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExampleFragmentViewModel::class.java)

        fab.clicks().subscribe { viewModel.updateEmoji() }

        viewModel.state.observe(this, Observer { emojiText.text = it.emoji })

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setTitle(R.string.app_name)
        toolbar.subtitle = null
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_example, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_help -> {
            view?.let { Snackbar.make(it, R.string.app_name, Snackbar.LENGTH_SHORT).show() }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}