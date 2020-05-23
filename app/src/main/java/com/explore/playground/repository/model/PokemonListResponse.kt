package com.explore.playground.repository.model

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonURL>?
)

data class PokemonURL(
    val name: String,
    val url: String
)