package com.example.budgettrackerapp

import android.widget.EditText
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class Budget(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "sana")
    val sana: String,

    @ColumnInfo(name = "price")
    val price: String,

    @ColumnInfo(name = "imageID")
    val imageID: String,

    @ColumnInfo(name = "note")
    val note: String,
)
