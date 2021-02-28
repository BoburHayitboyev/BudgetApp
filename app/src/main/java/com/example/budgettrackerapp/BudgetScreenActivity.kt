package com.example.budgettrackerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class BudgetScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_screen)

        val full_text = findViewById<TextView>(R.id.full_note)


        val title = intent.getStringExtra("title")
        val imageID = intent.getStringExtra("imageID")
        val note = intent.getStringExtra("note")
        val price = intent.getStringExtra("price")
        val sana = intent.getStringExtra("sana")

        var chiqim = "pul topgansiz"
        var da = "dan"

        if (imageID=="minus"){
            chiqim="pul yoqotgansiz"
            da="ga"
        }

        full_text.text = " Siz $sana kuni $title $da $price so'm $chiqim. " +
                "\n Qisqacha ma'lumot: $note"
    }
}