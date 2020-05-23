package com.explore.playground.mvvmnavfan

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.explore.playground.R
import com.explore.playground.base.BaseFragment
import com.explore.playground.repository.model.PokemonURL
import com.explore.playground.utils.EndlessScrollUtil
import com.explore.playground.utils.init
import com.explore.playground.utils.scrollListener
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

/**
 * A simple [Fragment] subclass.
 */
class PokemonListFragment : BaseFragment() {
    private lateinit var adapter: PokemonAdapter
    private lateinit var vm: PokemonVM
    private lateinit var scrollListener: EndlessScrollUtil
    override fun setLayoutId(): Int {
        return R.layout.fragment_pokemon_list
    }

    override fun setInitialAsset() {
        vm = ViewModelProvider(this).get(PokemonVM::class.java)
        rvPokemon.init(ctx)
        adapter = PokemonAdapter(ctx)
        adapter.addAllItem(vm.oldData)
        vm.data.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                adapter.addAllItem(it.toMutableList())
            }
        })
    }

    override fun setListener() {
        adapter.listen = object : PokemonAdapter.Listener {
            override fun onItemClicked(item: PokemonURL) {
                startFragment(
                    PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(
                        item.url
                    )
                )
            }
        }
        rvPokemon.adapter = adapter
        scrollListener = scrollListener {
            vm.getPokemon()
        }
        rvPokemon.addOnScrollListener(scrollListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vm.oldData.clear()
        vm.oldData.addAll(adapter.getItem())
    }

    override fun removeListener() {
        vm.data.removeObservers(viewLifecycleOwner)
    }

}
