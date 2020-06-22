package com.explore.playground.repository.db.daos

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.explore.playground.repository.model.PokemonURL
import com.explore.playground.utils.LiveOnce

interface PokemonURLDao {
    @Query("SELECT * FROM pokemon_list")
    fun getListPokemon(): LiveOnce<List<PokemonURL>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: PokemonURL)

    @Query("DELETE FROM pokemon_list")
    fun delete()
}