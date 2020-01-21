package com.explore.playground.room

import androidx.lifecycle.LiveData
import com.explore.playground.room.entity.Expense
import com.explore.playground.room.entity.ExpenseDAO

class Repository(private val dao: ExpenseDAO) {
    val expense: LiveData<List<Expense>> = dao.getList()
    suspend fun insert(expense: Expense) {
        dao.insert(expense)
    }
}