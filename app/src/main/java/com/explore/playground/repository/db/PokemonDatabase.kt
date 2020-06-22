package com.explore.playground.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.explore.playground.repository.db.daos.PokemonURLDao
import com.explore.playground.repository.model.PokemonURL

@Database(entities = [PokemonURL::class], version = 1, exportSchema = true)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonURLDao

    companion object {
        @Volatile
        var INSTANCE: PokemonDatabase? = null

        fun getDatabase(ctx: Context): PokemonDatabase {
            val tempInstance =
                INSTANCE
            tempInstance?.let {
                return it
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    PokemonDatabase::class.java,
                    "poke_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}