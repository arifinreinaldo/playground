package com.explore.playground.adapterAndRecyclerview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.explore.playground.R
import com.explore.playground.adapterAndRecyclerview.base.BaseRecylerAdapter
import com.explore.playground.adapterAndRecyclerview.base.BaseViewHolder
import com.explore.playground.adapterAndRecyclerview.base.HelperSwipe
import kotlinx.android.synthetic.main.adapter_list.view.*

data class Dummy(
    val title: String,
    val desc: String,
    val activate: Boolean = false
)

class DummyRecylerAdapter(val ctx: Context) :
    BaseRecylerAdapter<Dummy, DummyRecylerAdapter.ViewHolder>(ctx), HelperSwipe.Emitter {

    lateinit var listen: DummyListener

    interface DummyListener {
        fun onItemClicked(item: Dummy)
    }

    class ViewHolder(itemView: View, val listener: DummyListener) :
        BaseViewHolder<Dummy>(itemView) {
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

    override fun onSwipe(position: Int, direction: Int) {
        removeAtPosition(position)
    }

    override fun onMove(before: Int, after: Int) {
        Toast.makeText(ctx, "lelel", Toast.LENGTH_SHORT).show()
    }
}