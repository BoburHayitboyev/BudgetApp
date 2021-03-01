package com.example.budgettrackerapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.budgettrackerapp.databinding.ActivityBudgetAddBinding
import kotlinx.coroutines.*
import java.util.*

class BudgetAddActivity : AppCompatActivity() {

    lateinit var binding: ActivityBudgetAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBudgetAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val job = Job()

        val uiScope = CoroutineScope(Dispatchers.Main + job)

        val database = BudgetDatabase.getInstance(application)

        val budgetDao = database.budgetDao()

        val sharedPreferences = getSharedPreferences("AllMoney", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        val imageID = intent.getStringExtra("image").toString()

        val rightNow: Calendar = Calendar.getInstance()

        val year = rightNow.get(Calendar.YEAR).toString()
        val month: String
        val day: String

        month = if ((rightNow.get(Calendar.MONTH) + 1) < 10) {
            "0" + (rightNow.get(Calendar.MONTH) + 1).toString()
        } else {
            rightNow.get(Calendar.MONTH).toString()
        }

        day = if (rightNow.get(Calendar.DAY_OF_MONTH) < 10) {
            "0" + rightNow.get(Calendar.DAY_OF_MONTH).toString()
        } else {
            rightNow.get(Calendar.DAY_OF_MONTH).toString()
        }

        binding.add.setOnClickListener {

            val title = binding.title.text.toString()
            val price = binding.price.text.toString()
            val note = binding.note.text.toString()

            if (title=="" || price == ""){
                Toast.makeText(this, "To'liq ma'lumot kiriting", Toast.LENGTH_SHORT).show()
            }else {
                val sana = "$day.$month.$year"

                val summ = sharedPreferences.getInt("summa",0)

                if (imageID == "plus"){
                    val sum = summ+price.toInt()
                    editor.putInt("summa",sum)
                    editor.apply()
                }else{
                    val sum = summ-price.toInt()
                    editor.putInt("summa",sum)
                    editor.apply()
                }

                val budget = Budget(
                    title = title,
                    sana = sana,
                    price = price,
                    imageID = imageID,
                    note = note
                )

                uiScope.launch {
                    withContext(Dispatchers.IO) {
                        budgetDao.insertBudget(budget)
                    }
                }
                finish()
            }}
        }
    }
