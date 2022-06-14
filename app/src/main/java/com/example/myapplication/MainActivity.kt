package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bKitchen:Button = findViewById(R.id.bKitchen)
        val bClose:Button =findViewById(R.id.bClose)
        val bData:Button = findViewById(R.id.bData)


        bKitchen.setOnClickListener {
            val i = Intent(this,KitchenActivity::class.java)
            startActivity(i)
        }
        bClose.setOnClickListener {
            val i =Intent(this, CloseActivity::class.java)
            startActivity(i)
        }
        bData.setOnClickListener {
            val i =Intent(this,DataActivity::class.java)
            startActivity(i)
        }

    }
}