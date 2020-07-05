package com.explore.playground.repository.db.daos

import androidx.room.*
import com.explore.playground.repository.model.PokemonURL

@Dao
interface PokemonURLDao {
    @Query("SELECT * FROM pokemon_list")
    suspend fun getListPokemon(): List<PokemonURL>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonURL)

    @Query("DELETE FROM pokemon_list")
    fun delete()

    @Delete
    suspend fun deleteData(pokemon: PokemonURL)

    @Query("SELECT * FROM pokemon_list WHERE url = :url")
    suspend fun getPokemonbyID(url: String): PokemonURL?

}