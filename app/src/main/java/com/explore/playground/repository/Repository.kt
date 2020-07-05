package com.explore.playground.repository

import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.explore.playground.repository.db.PokemonDatabase
import com.explore.playground.repository.db.daos.PokemonURLDao
import com.explore.playground.repository.model.PokemonURL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit

const val path = "https://pokeapi.co/api/v2/"
fun getPath(namePath: String): String {
    return path + namePath
}

object Repository {
    private lateinit var db: PokemonDatabase
    private lateinit var dao: PokemonURLDao
    fun init(ctx: Context) {
        val okHTTP =
            OkHttpClient().newBuilder().connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val request = chain.request()
                        var response = chain.proceed(request)
                        var tryCount = 0
                        while (!response.isSuccessful && tryCount < 3) {
                            tryCount++
                            response = chain.proceed(request)
                        }
                        return response
                    }
                }).build()
        AndroidNetworking.initialize(ctx, okHTTP)

        db = PokemonDatabase.getDatabase(ctx)
        dao = db.pokemonDao()
    }

    suspend fun insertPokemon(obj: PokemonURL): Boolean {
        var rst = false
        withContext(Dispatchers.IO) {
            dao.getPokemonbyID(obj.url)?.let {
                dao.deleteData(obj)
            } ?: run {
                dao.insert(obj)
                rst = true
            }
        }
        return rst
    }

    suspend fun getPokemons(): List<PokemonURL> {
        return withContext(Dispatchers.IO) {
            dao.getListPokemon()
        }
    }

    suspend fun deletePokemon(pokemonURL: PokemonURL) {
        withContext(Dispatchers.IO) {
            dao.deleteData(pokemonURL)
        }
    }


    //Login
    suspend fun getPokemon(next: String, success: (String?) -> Unit, error: (String?) -> Unit) {
        var path = getPath("pokemon")
        if (next.isNotEmpty()) {
            path = next
        }
        AndroidNetworking.get(path)
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(anError.toString())
                }
            })
    }

    fun getPokemonDetail(url: String, success: (String?) -> Unit, error: (String?) -> Unit) {
        AndroidNetworking.get(url)
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(anError.toString())
                }
            })
    }
}