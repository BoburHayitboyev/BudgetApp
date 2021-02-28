package com.example.budgettrackerapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgettrackerapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = BudgetDatabase.getInstance(application)

        val budgetDao = database.budgetDao()

        val adapter = BudgetAdapter(ArrayList(), object : OnItemClickListener {
            override fun onCLick(budget: Budget) {
//                val intent = Intent(this@MainActivity, BudgetScreenActivity::class.java)
//
//                intent.putExtra("title",budget.title)
//                intent.putExtra("imageID",budget.imageID)
//                intent.putExtra("note",budget.note)
//                intent.putExtra("price",budget.price)
//                intent.putExtra("sana",budget.sana)
//
//                startActivity(intent)

                val builder  = AlertDialog.Builder(this@MainActivity)

                builder.setTitle(budget.title)

                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            }
        })

        val sharedPreferences = getSharedPreferences("AllMoney", Context.MODE_PRIVATE)

        Log.d("MainActivit_OnCreate","OnCreatga keldi")
        val sum = sharedPreferences.getInt("summa",0).toString()

        binding.allMoney.text = "$sum so'm"

        binding.recyclerView.apply {
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(context)
        }

        binding.imgAdd.setOnClickListener {
            val intent = Intent(this, BudgetAddActivity::class.java)
            intent.putExtra("image","plus")
            startActivity(intent)
        }

        binding.imgMinus.setOnClickListener {
            val intent = Intent(this, BudgetAddActivity::class.java)
            intent.putExtra("image","minus")
            startActivity(intent)
        }

        val liveData = budgetDao.queryAllBudget()

        liveData.observe(this, {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("AllMoney", Context.MODE_PRIVATE)

        Log.d("MainActivit_OnCreate","OnCreatga keldi")
        val sum = sharedPreferences.getInt("summa",0).toString()

        binding.allMoney.text = "$sum so'm"
    }

}