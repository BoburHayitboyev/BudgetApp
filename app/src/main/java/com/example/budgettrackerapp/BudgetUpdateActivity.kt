package com.example.budgettrackerapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.budgettrackerapp.databinding.ActivityBudgetUpdateBinding
import kotlinx.coroutines.*

class BudgetUpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityBudgetUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBudgetUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val job = Job()

        val uiScope = CoroutineScope(Dispatchers.Main + job)

        val database = BudgetDatabase.getInstance(application)

        val budgetDao = database.budgetDao()

        val sharedPreferences = getSharedPreferences("AllMoney", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        val id = intent.getStringExtra("id").toString()
        val title = intent.getStringExtra("title").toString()
        val note = intent.getStringExtra("note").toString()
        val price = intent.getStringExtra("price").toString()
        val imageID = intent.getStringExtra("imageID").toString()
        val sana = intent.getStringExtra("sana").toString()

        binding.note.setText(note)
        binding.price.setText(price)
        binding.title.setText(title)

        var sum = sharedPreferences.getInt("summa", 0)

        binding.update.setOnClickListener {

            if (title == "" || price == "") {
                Toast.makeText(this, "To'liq malumot kiriting", Toast.LENGTH_SHORT).show()
            } else {

                    if (imageID == "minus") {
                        sum += price.toInt()
                        sum -= binding.price.text.toString().toInt()
                    } else {
                        sum -= price.toInt()
                        sum += binding.price.text.toString().toInt()
                    }

                editor.putInt("summa",sum)
                editor.apply()

                val budget = Budget(
                    id = id.toInt(),
                    title = binding.title.text.toString(), note = binding.note.text.toString(),
                    price = binding.price.text.toString(), sana = sana, imageID = imageID
                )

                uiScope.launch {
                    withContext(Dispatchers.IO) {

                        budgetDao.update(budget)
                    }
                }

                finish()
            }
        }

    }
}