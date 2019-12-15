package eu.darken.androidkotlinstarter.main.ui.anotherfrag

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import eu.darken.androidkotlinstarter.R

class NameAdapter : RecyclerView.Adapter<NameAdapter.ViewHolder>() {

    val data = mutableListOf<NameData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(parent: ViewGroup)
        : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.another_fragment_adapter_data_line, parent, false)
    ) {

        @BindView(R.id.name) lateinit var nameText: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(item: NameData) {
            nameText.text = item.name
        }

    }

}