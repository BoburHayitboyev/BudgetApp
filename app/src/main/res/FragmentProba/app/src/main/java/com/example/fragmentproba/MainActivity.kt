package com.example.fragmentproba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val button_2 = findViewById<Button>(R.id.button_2)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        button.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.framelayout,firstFragment).commit()
        }

        button_2.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.framelayout,secondFragment).commit()
        }
    }
}