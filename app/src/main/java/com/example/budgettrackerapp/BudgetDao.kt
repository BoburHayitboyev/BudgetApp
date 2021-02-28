package com.example.budgettrackerapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BudgetDao {

    @Query("SELECT * FROM budget")
    fun queryAllBudget() : LiveData<List<Budget>>

    @Insert
    fun insertBudget(budget: Budget)
}