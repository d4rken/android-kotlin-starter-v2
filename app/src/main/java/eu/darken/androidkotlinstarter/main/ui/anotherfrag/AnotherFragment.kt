package eu.darken.androidkotlinstarter.main.ui.anotherfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import eu.darken.androidkotlinstarter.R
import eu.darken.androidkotlinstarter.common.dagger.AutoInject
import eu.darken.androidkotlinstarter.common.smart.SmartFragment
import eu.darken.androidkotlinstarter.common.vdc.VDCSource
import eu.darken.androidkotlinstarter.common.vdc.vdcsAssisted
import javax.inject.Inject


class AnotherFragment : SmartFragment(), AutoInject {

    @Inject lateinit var vdcSource: VDCSource.Factory
    private val vdc: AnotherFragmentVDC by vdcsAssisted({ vdcSource }, { factory, handle ->
        factory as AnotherFragmentVDC.Factory
        factory.create(handle)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.another_fragment, container, false)
        addUnbinder(ButterKnife.bind(this, layout))
        return layout
    }
}
