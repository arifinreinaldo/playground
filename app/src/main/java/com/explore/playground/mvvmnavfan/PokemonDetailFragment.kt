package com.explore.playground.mvvmnavfan

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.explore.playground.R
import com.explore.playground.base.BaseFragment
import com.explore.playground.utils.load
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*

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
        vm.detail.observe(viewLifecycleOwner, Observer { it ->
            it.showOnce()?.let { detail ->
                detail.sprites.front_default?.let { image ->
                    ivSprite.load(image, this.ctx)
                }
                detail.name?.let {
                    tvTitle.text = it.capitalize()
                }
            }
        })
        vm.getPokemons()
    }

    override fun setListener() {

    }

    override fun removeListener() {

    }
}
