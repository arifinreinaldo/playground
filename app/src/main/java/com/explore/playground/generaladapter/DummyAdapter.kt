package com.explore.playground.generaladapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.explore.playground.R
import com.explore.playground.generaladapter.base.BaseAdapter
import com.explore.playground.generaladapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.adapter_list.view.*

data class Dummy(
    val title: String,
    val desc: String
)

interface DummyListener {
    fun onItemClicked(item: Dummy)
}

class DummyAdapter(ctx: Context) : BaseAdapter<Dummy, DummyAdapter.ViewHolder>(ctx) {

    lateinit var listen : DummyListener

    class ViewHolder(itemView: View, val listener: DummyListener) : BaseViewHolder<Dummy>(itemView) {
        val tvTitle = itemView.tvTitle
        val llRecord = itemView.llRecord
        val tvDesc = itemView.tvDesc
        override fun onBind(item: Dummy) {
            tvTitle.text = item.title
            tvDesc.text = item.desc
            llRecord.setOnClickListener {
                listener?.onItemClicked(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflate(R.layout.adapter_list, parent), listen)
    }
}