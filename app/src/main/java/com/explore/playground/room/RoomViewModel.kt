package com.explore.playground.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.explore.playground.room.entity.Expense
import com.explore.playground.room.entity.RoomDB
import kotlinx.coroutines.launch

class RoomViewModel(app: Application) : AndroidViewModel(app) {
    private lateinit var repo: Repo
    private lateinit var listExpense: LiveData<List<Expense>>

    init {
        val dao = RoomDB.getDatabaes(app).expenseDao()
        repo = Repo(dao)
        listExpense = repo.expense
    }

    fun insert(expense: Expense) = viewModelScope.launch {
        repo.insert(expense)
    }
}