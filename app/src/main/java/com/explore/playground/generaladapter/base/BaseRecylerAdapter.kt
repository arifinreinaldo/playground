package com.explore.playground.generaladapter.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView


open class BaseRecylerAdapter<T, VH : BaseViewHolder<T>> :
    RecyclerView.Adapter<VH>() {

    var items: MutableList<T> = mutableListOf()

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
        return LayoutInflater.from(parent.context).inflate(layout, parent, root)
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
        holder.onBind(item)
    }

}

abstract class BaseViewHolder<T>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(item: T)
}