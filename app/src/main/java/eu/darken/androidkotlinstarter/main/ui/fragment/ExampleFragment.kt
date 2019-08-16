package eu.darken.androidkotlinstarter.main.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.view.clicks
import eu.darken.androidkotlinstarter.R
import eu.darken.androidkotlinstarter.common.dagger.AutoInject
import eu.darken.androidkotlinstarter.common.smart.SmartFragment
import eu.darken.androidkotlinstarter.common.vdc.VDCSource
import eu.darken.androidkotlinstarter.common.vdc.vdcsAssisted
import javax.inject.Inject


class ExampleFragment : SmartFragment(), AutoInject {
    companion object {
        fun newInstance(): Fragment = ExampleFragment()
    }

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.emoji_text) lateinit var emojiText: TextView
    @BindView(R.id.fab) lateinit var fab: FloatingActionButton

    @Inject lateinit var vdcSource: VDCSource.Factory
    private val vdc: ExampleFragmentVDC by vdcsAssisted({ vdcSource }, { factory, handle ->
        factory as ExampleFragmentVDC.Factory
        factory.create(handle, arguments)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
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
        fab.clicks().subscribe { vdc.updateEmoji() }

        vdc.state.observe(this, Observer { emojiText.text = it.emoji })

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
