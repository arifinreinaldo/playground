package com.explore.playground.mvvmnavfan

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.explore.playground.R
import com.explore.playground.adapterAndRecyclerview.base.BaseRecylerAdapter
import com.explore.playground.adapterAndRecyclerview.base.BaseViewHolder
import com.explore.playground.repository.model.PokemonURL
import com.explore.playground.utils.load
import kotlinx.android.synthetic.main.adapter_pokemon.view.*


class PokemonAdapter(ctx: Context) :
    BaseRecylerAdapter<PokemonURL, PokemonAdapter.ViewHolder>(ctx) {

    lateinit var listen: Listener

    interface Listener {
        fun onItemClicked(item: PokemonURL, image: ImageView)
        fun onSpriteClicked(item: PokemonURL)
        fun isCheck(item: PokemonURL): Boolean
    }

    class ViewHolder(itemView: View, private val listener: Listener) :
        BaseViewHolder<PokemonURL>(itemView) {
        override fun onBind(item: PokemonURL) {
            itemView.llRow.setOnClickListener {
                listener.onItemClicked(item, itemView.ivPokemon)
            }
            itemView.ivPokemon.load("https://i.pinimg.com/564x/72/e3/29/72e3297ef891c59f9cc57a5452eaece9.jpg")
            itemView.ivPokemon.transitionName = item.url
            itemView.tvPokemon.text = item.name
            itemView.ivSprite.setOnClickListener {
                listener.onSpriteClicked(item)
            }
            if (listener.isCheck(item)) {
                itemView.ivSprite.setImageResource(R.drawable.ic_heart)
            } else {
                itemView.ivSprite.setImageResource(R.drawable.ic_heart_empty)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            inflate(R.layout.adapter_pokemon, parent),
            listen
        )
    }
}