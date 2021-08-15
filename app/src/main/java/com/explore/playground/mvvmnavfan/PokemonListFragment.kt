package com.explore.playground.mvvmnavfan

import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.explore.playground.R
import com.explore.playground.base.BaseFragment
import com.explore.playground.repository.model.PokemonURL
import com.explore.playground.utils.*
import com.google.android.material.transition.Hold
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
        val dividerItemDecoration = DividerItemDecoration(
            ctx,
            LinearLayout.VERTICAL
        )
        exitTransition = Hold()

        vm = ViewModelProvider(this).get(PokemonVM::class.java)
        rvPokemon.init(ctx)
        adapter = PokemonAdapter(ctx)
        rvPokemon.addItemDecoration(dividerItemDecoration)
        adapter.addAllItem(vm.oldData)
        vm.data.observe(viewLifecycleOwner, Observer { event ->
            event.showOnce()?.let {
                adapter.addAllItem(it.toMutableList())
            }
        })
        vm.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                tvLoading.showView()
            } else {
                tvLoading.hideView()
            }
        })
    }

    override fun setListener() {
        adapter.listen = object : PokemonAdapter.Listener {
            override fun onItemClicked(item: PokemonURL, image: ImageView) {
                val extras = FragmentNavigatorExtras(
                    image to "pokemon"
                )
                findNavController().navigate(
                    PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(
                        item.url
                    ), extras
                )
            }

            override fun onSpriteClicked(item: PokemonURL) {

            }

            override fun isCheck(item: PokemonURL): Boolean {
                return vm.checkPokemon(item)
            }
        }
        rvPokemon.adapter = adapter
        scrollListener = scrollListener {
            vm.fetchPokemon()
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
