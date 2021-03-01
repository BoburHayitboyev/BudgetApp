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

        val job = Job()

        val uiScope = CoroutineScope(Dispatchers.Main + job)

        val database = BudgetDatabase.getInstance(application)

        val budgetDao = database.budgetDao()

        val sharedPreferences = getSharedPreferences("AllMoney", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        // Item click bolganda chiqadigan joyi "Alert Dialog"
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

                val builder = AlertDialog.Builder(this@MainActivity)

                val turi = if (budget.imageID == "minus") {
                    "Chiqim nomi: "
                } else {
                    "Kirim nomi: "
                }

                builder.setTitle("Qisqacha ma'lumotlar:")

                builder.setMessage(
                    turi + budget.title + "\n" +
                            "Sanasi: " + budget.sana + "\n" +
                            "Summasi: " + budget.price + " so'm"
                )

                builder.setPositiveButton("Tahrirlash") { dialogInterface, which ->
                    val intent = Intent(this@MainActivity, BudgetUpdateActivity::class.java)

                    intent.putExtra("title", budget.title)
                    intent.putExtra("id", budget.id.toString())
                    intent.putExtra("note", budget.note)
                    intent.putExtra("price", budget.price)
                    intent.putExtra("sana", budget.sana)
                    intent.putExtra("imageID", budget.imageID)

                    startActivity(intent)
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            }
        }, object : OnlongClickListener {
            override fun longClick(budget: Budget) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Aniq shuni o'chirmoqchimisiz?")
                builder.setMessage(
                    "Agar bu ma'lumotni o'chrib yuborsangiz hech qachon " +
                            "ortga qaytarib bo'maydi.\n"
                )

                var sum = sharedPreferences.getInt("summa",0)

                val price = budget.price.toInt()

                builder.setPositiveButton("Ha") { dialogInterface, which ->
                    if (budget.imageID == "minus") {
                        sum+=price
                    }else{
                        sum-=price
                    }

                    editor.putInt("summa",sum)
                    binding.allMoney.text = "$sum so'm"
                    editor.apply()

                    uiScope.launch {
                        withContext(Dispatchers.IO) {
                            budgetDao.delete(budget)
                        }
                    }
                }

                builder.setNegativeButton("Yo'q") { dialogInterface, which ->

                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            }

        })

        Log.d("MainActivit_OnCreate", "OnCreatga keldi")
        val sum = sharedPreferences.getInt("summa", 0).toString()

        binding.allMoney.text = "$sum so'm"

        binding.recyclerView.apply {
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(context)
        }


        binding.imgAdd.setOnClickListener {
            val intent = Intent(this, BudgetAddActivity::class.java)
            intent.putExtra("image", "plus")
            startActivity(intent)
        }

//        binding.imgAdd.setOnLongClickListener {
//            Toast.makeText(this, "Salom", Toast.LENGTH_SHORT).show()
//            return@setOnLongClickListener false
//        }

        binding.imgMinus.setOnClickListener {
            val intent = Intent(this, BudgetAddActivity::class.java)
            intent.putExtra("image", "minus")
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

        Log.d("MainActivit_OnCreate", "OnStartga keldi")
        val sum = sharedPreferences.getInt("summa", 0).toString()

        binding.allMoney.text = "$sum so'm"
    }

}