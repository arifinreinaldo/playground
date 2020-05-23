package com.explore.playground.mvvmnavfan

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.explore.playground.R
import com.explore.playground.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class PokemonDetailFragment : BaseFragment() {
    private lateinit var vm: PokemonVM
    override fun setLayoutId(): Int {
        return R.layout.fragment_pokemon_detail
    }

    override fun setInitialAsset() {
        vm = ViewModelProvider(this).get(PokemonVM::class.java)
        val args: PokemonDetailFragmentArgs by navArgs()
        vm.getPokemonDetail(args.url)
    }

    override fun setListener() {

    }

    override fun removeListener() {

    }
}
