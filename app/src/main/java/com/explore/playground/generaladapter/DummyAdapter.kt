package com.explore.playground.generaladapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.explore.playground.R
import com.explore.playground.generaladapter.base.BaseAdapter
import com.explore.playground.generaladapter.base.BaseListener
import com.explore.playground.generaladapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.adapter_list.view.*

data class Dummy(
    val title: String,
    val desc: String
)

interface DummyListener<T> : BaseListener {
    fun onItemClicked(item: T)
}

class DummyAdapter(ctx: Context) :
    BaseAdapter<Dummy, DummyListener<Dummy>, DummyAdapter.ViewHolder>(ctx) {
    class ViewHolder(itemView: View) : BaseViewHolder<Dummy, DummyListener<Dummy>>(itemView) {
        val tvTitle = itemView.tvTitle
        val llRecord = itemView.llRecord
        val tvDesc = itemView.tvDesc
        override fun onBind(item: Dummy, listen: DummyListener<Dummy>?) {
            tvTitle.text = item.title
            tvDesc.text = item.desc
            llRecord.setOnClickListener {
                listen?.onItemClicked(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflate(R.layout.adapter_list, parent))
    }
}