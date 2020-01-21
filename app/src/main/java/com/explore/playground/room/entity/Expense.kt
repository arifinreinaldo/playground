package com.explore.playground.room.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey @ColumnInfo(name = "expense_id") val id: String,
    @NonNull @ColumnInfo(name = "expense_type") val type: String,
    @ColumnInfo(name = "expense_total") val total: Int
)