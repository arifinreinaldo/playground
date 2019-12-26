package com.explore.playground.generaladapter.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView


open class BaseAdapter<T, L : BaseListener, VH : BaseViewHolder<T, L>>(ctx: Context) :
    RecyclerView.Adapter<VH>() {

    var items: MutableList<T> = mutableListOf()
    lateinit var listen: L
    var layoutInflater: LayoutInflater = LayoutInflater.from(ctx)

    fun setListener(listen: L) {
        this.listen = listen
    }

    fun setItem(items: MutableList<T>) {
        items?.let {
            this.items.clear()
            this.items = it

        }
    }

    fun getItem(): MutableList<T> {
        return items
    }

    fun addItem(item: T): Boolean {
        var ret = false
        item?.let {
            items.add(item)
            notifyItemInserted(items.size - 1);
            ret = true
        }
        return ret
    }

    fun addAllItem(item: MutableList<T>): Boolean {
        var ret = false
        item?.let {
            items.addAll(item)
            notifyDataSetChanged()
            ret = true
        }
        return ret
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun remove(item: T): Boolean {
        var ret = false
        val position = items.indexOf(item)
        if (position > -1) {
            items.removeAt(position)
            ret = true
            notifyItemRemoved(position)
        }
        return ret
    }

    fun isEmpty(): Boolean {
        return itemCount == 0
    }

    fun inflate(@LayoutRes layout: Int, @Nullable parent: ViewGroup, root: Boolean): View {
        return layoutInflater.inflate(layout, parent, root)
    }

    fun inflate(@LayoutRes layout: Int, @Nullable parent: ViewGroup): View {
        return inflate(layout, parent, false)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return items?.count()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items.get(position)
        if (::listen.isInitialized) {
            holder.onBind(item, listen)
        } else {
            holder.onBind(item, null)
        }
    }

}

abstract class BaseViewHolder<T, L : BaseListener>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(item: T, listen: L?)
}

interface BaseListener {

}