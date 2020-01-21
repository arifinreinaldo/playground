package com.explore.playground.room.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(ExpenseDAO::class),
    version = 1,
    exportSchema = true
)
abstract class RoomDB : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDAO

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabaes(ctx: Context): RoomDB {
            val instance = INSTANCE
            instance?.let {
                return it
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, RoomDB::class.java, "word_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}