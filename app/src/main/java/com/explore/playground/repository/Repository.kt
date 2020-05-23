package com.explore.playground.repository

import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit

const val path = "https://pokeapi.co/api/v2/"
fun getPath(namePath: String): String {
    return path + namePath
}

object Repository {
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
    }


    //Login
    fun getPokemon(next: String, success: (String?) -> Unit, error: (String?) -> Unit) {
        var path = getPath("pokemon")
        if (next.isNotEmpty()) {
            path = next
        }
        AndroidNetworking.get(path)
//            .addQueryParameter("limit", "50")
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