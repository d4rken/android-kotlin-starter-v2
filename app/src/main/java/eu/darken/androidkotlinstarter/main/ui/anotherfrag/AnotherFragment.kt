package eu.darken.androidkotlinstarter.main.ui.anotherfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import eu.darken.androidkotlinstarter.R
import eu.darken.androidkotlinstarter.common.dagger.AutoInject
import eu.darken.androidkotlinstarter.common.observe2
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

    @BindView(R.id.label) lateinit var labelText: TextView
    @BindView(R.id.action_update) lateinit var updateAction: Button
    @BindView(R.id.data_list) lateinit var dataList: RecyclerView

    private val nameAdapter: NameAdapter = NameAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.another_fragment, container, false)
        addUnbinder(ButterKnife.bind(this, layout))

        dataList.layoutManager = LinearLayoutManager(requireContext())
        dataList.adapter = nameAdapter

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        vdc.state.observe2(this) { state ->
            labelText.text = state.userName

            nameAdapter.data.add(NameData("Hi"))
            nameAdapter.notifyDataSetChanged()
        }

        updateAction.setOnClickListener {
            vdc.updateUserName()
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
