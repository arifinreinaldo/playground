package com.explore.playground.easycamera

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.explore.playground.R
import com.explore.playground.generaladapter.base.BaseRecylerAdapter
import com.explore.playground.generaladapter.base.BaseViewHolder
import com.explore.playground.utils.load
import kotlinx.android.synthetic.main.adapter_image.view.*
import pl.aprilapps.easyphotopicker.MediaFile

data class MediaFile(
    val title: String,
    val desc: String
)

class ImageAdapter(ctx: Context) :
    BaseRecylerAdapter<MediaFile, ImageAdapter.ViewHolder>(ctx) {

    lateinit var listen: MediaFileListener

    interface MediaFileListener {
        fun onItemClicked(item: MediaFile)
    }

    class ViewHolder(itemView: View, val listener: MediaFileListener) :
        BaseViewHolder<MediaFile>(itemView) {
        val ivImage = itemView.ivImage
        override fun onBind(item: MediaFile) {
            ivImage.load(item.file)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflate(R.layout.adapter_image, parent), listen)
    }
}