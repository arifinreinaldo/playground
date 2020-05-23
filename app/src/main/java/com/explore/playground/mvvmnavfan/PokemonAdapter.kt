package com.explore.playground.mvvmnavfan

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.explore.playground.R
import com.explore.playground.adapterAndRecyclerview.base.BaseRecylerAdapter
import com.explore.playground.adapterAndRecyclerview.base.BaseViewHolder
import com.explore.playground.repository.model.PokemonURL
import kotlinx.android.synthetic.main.adapter_pokemon.view.*


class PokemonAdapter(ctx: Context) :
    BaseRecylerAdapter<PokemonURL, PokemonAdapter.ViewHolder>(ctx) {

    lateinit var listen: Listener

    interface Listener {
        fun onItemClicked(item: PokemonURL)
    }

    class ViewHolder(itemView: View, val listener: Listener) :
        BaseViewHolder<PokemonURL>(itemView) {
        override fun onBind(item: PokemonURL) {
            itemView.llRow.setOnClickListener {
                listener?.onItemClicked(item)
            }
            itemView.tvPokemon.text = item.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            inflate(R.layout.adapter_pokemon, parent),
            listen
        )
    }
}