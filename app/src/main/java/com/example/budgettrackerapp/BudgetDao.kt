package com.example.budgettrackerapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BudgetDao {

    @Query("SELECT * FROM budget ORDER BY id DESC")
    fun queryAllBudget() : LiveData<List<Budget>>

    @Update
    fun update(budget: Budget)

    @Delete
    fun delete(budget: Budget)
    @Insert
    fun insertBudget(budget: Budget)
}