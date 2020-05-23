package com.explore.playground.mvvmnavfan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.explore.playground.repository.Repository
import com.explore.playground.repository.model.PokemonListResponse
import com.explore.playground.repository.model.PokemonURL
import com.explore.playground.utils.getMoshi
import com.explore.playground.utils.write

//this is MVVM + android networking examples
class PokemonVM : ViewModel() {
    var score: Int = 0
    private var path = ""

    init {
        getPokemon()
    }

    fun getPokemon(): LiveData<List<PokemonURL>> {
        var pokemons = MutableLiveData<List<PokemonURL>>()
        Repository.getPokemon(path, { success ->
            write(success.toString())
            val jsonAdapter =
                getMoshi().adapter<PokemonListResponse>(PokemonListResponse::class.java)
            val result = jsonAdapter.fromJson(success)
            result?.let {
                if (it.count > 0) {
                    it.results?.let { result ->
                        pokemons.value = result
                    }
                } else {
                    //nodata
                }
                path = ""
                result.next?.let {
                    path = it
                }
            }
        }, { error ->
            write(error.toString())
        })
        return pokemons
    }

    fun getPokemonDetail(url: String) {
        Repository.getPokemonDetail(url, { success ->
            write(success.toString())
        }, { error ->
            write(error.toString())
        })
    }
}