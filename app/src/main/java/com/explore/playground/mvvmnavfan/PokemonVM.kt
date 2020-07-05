package com.explore.playground.mvvmnavfan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.explore.playground.repository.Repository
import com.explore.playground.repository.model.PokemonDetailResponse
import com.explore.playground.repository.model.PokemonListResponse
import com.explore.playground.repository.model.PokemonURL
import com.explore.playground.utils.LiveOnce
import com.explore.playground.utils.getMoshi
import com.explore.playground.utils.write
import kotlinx.coroutines.launch

//this is MVVM + android networking examples
class PokemonVM(application: Application) : AndroidViewModel(application) {
    var score: Int = 0
    var isLoading = MutableLiveData<Boolean>()
    private var path = ""
    var data = MutableLiveData<LiveOnce<List<PokemonURL>>>()
    var oldData = mutableListOf<PokemonURL>()
    var detail = MutableLiveData<LiveOnce<PokemonDetailResponse>>()
    var saved = mutableMapOf<String, PokemonURL>()


    init {
        getPokemons()
        fetchPokemon()
        isLoading.value = false
    }

    fun insertPokemon(obj: PokemonURL) {
        viewModelScope.launch {
            Repository.insertPokemon(obj)
        }
    }

    fun deletePokemon(obj: PokemonURL) {
        viewModelScope.launch {
            Repository.deletePokemon(obj)
        }
    }

    fun getPokemons() {
        viewModelScope.launch {
            saved.clear()
            Repository.getPokemons().forEach {
                saved.put(it.url, it)
            }
        }
    }

    fun checkPokemon(pokemonURL: PokemonURL): Boolean {
        return saved.containsKey(pokemonURL.url)
    }

    fun fetchPokemon() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                Repository.getPokemon(path, { success ->
                    success?.let {
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
                    }
                }, { error ->
                    write(error.toString())
                })
            } catch (e: Exception) {

            } finally {
                isLoading.value = false
            }
        }
    }

    fun getPokemonDetail(url: String) {
        Repository.getPokemonDetail(url, { success ->
            success?.let {
                write(success.toString())
                val jsonAdapter =
                    getMoshi().adapter<PokemonDetailResponse>(PokemonDetailResponse::class.java)
                val result = jsonAdapter.fromJson(success)
                result?.let {
                    detail.value = LiveOnce(it)
                }
            }
        }, { error ->
            write(error.toString())
        })
    }
}