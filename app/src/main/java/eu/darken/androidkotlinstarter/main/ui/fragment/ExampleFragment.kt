package eu.darken.androidkotlinstarter.main.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.view.clicks
import eu.darken.androidkotlinstarter.R
import eu.darken.androidkotlinstarter.common.dagger.AutoInject
import eu.darken.androidkotlinstarter.common.observe2
import eu.darken.androidkotlinstarter.common.smart.SmartFragment
import eu.darken.androidkotlinstarter.common.vdc.VDCSource
import eu.darken.androidkotlinstarter.common.vdc.vdcsAssisted
import javax.inject.Inject


class ExampleFragment : SmartFragment(), AutoInject {

    private val navArgs by navArgs<ExampleFragmentArgs>()

    @Inject lateinit var vdcSource: VDCSource.Factory
    private val vdc: ExampleFragmentVDC by vdcsAssisted({ vdcSource }, { factory, handle ->
        factory as ExampleFragmentVDC.Factory
        factory.create(handle, navArgs.exampleArgument)
    })

    @BindView(R.id.emoji_text) lateinit var emojiText: TextView
    @BindView(R.id.fab) lateinit var fab: FloatingActionButton

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
        fab.clicks().subscribe {
            findNavController().navigate(R.id.action_exampleFragment_to_anotherFragment)
        }

        vdc.state.observe2(this) { emojiText.text = it.emoji }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_example, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_reset -> {
            vdc.updateEmoji()
            true
        }
        R.id.action_help -> {
            Snackbar.make(requireView(), R.string.app_name, Snackbar.LENGTH_SHORT).show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
