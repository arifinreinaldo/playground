package com.explore.playground.mvvmnavfan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.explore.playground.repository.Repository
import com.explore.playground.repository.model.PokemonDetailResponse
import com.explore.playground.repository.model.PokemonListResponse
import com.explore.playground.repository.model.PokemonURL
import com.explore.playground.utils.LiveOnce
import com.explore.playground.utils.getMoshi
import com.explore.playground.utils.write

//this is MVVM + android networking examples
class PokemonVM : ViewModel() {
    var score: Int = 0
    var isLoading = false
    private var path = ""
    var data = MutableLiveData<LiveOnce<List<PokemonURL>>>()
    var oldData = mutableListOf<PokemonURL>()
    var detail = MutableLiveData<LiveOnce<PokemonDetailResponse>>()

    init {
        getPokemon()
    }

    fun getPokemon() {
        if (!isLoading) {
            isLoading = true
            Repository.getPokemon(path, { success ->
                isLoading = false
                write(success.toString())
                val jsonAdapter =
                    getMoshi().adapter<PokemonListResponse>(PokemonListResponse::class.java)
                val result = jsonAdapter.fromJson(success)
                result?.let {
                    if (it.count > 0) {
                        it.results?.let { result ->
                            data.value = LiveOnce(result)
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
                isLoading = false
                write(error.toString())
            })
        }
    }

    fun getPokemonDetail(url: String) {
        Repository.getPokemonDetail(url, { success ->
            write(success.toString())
            val jsonAdapter =
                getMoshi().adapter<PokemonDetailResponse>(PokemonDetailResponse::class.java)
            val result = jsonAdapter.fromJson(success)
            result?.let {
                detail.value = LiveOnce(it)
            }
        }, { error ->
            write(error.toString())
        })
    }
}