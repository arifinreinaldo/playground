package com.explore.playground.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonURL>?
)

@Entity(tableName = "pokemon_list")
data class PokemonURL(
    @PrimaryKey @ColumnInfo(name = "pokemon_name") val name: String,
    val url: String
)