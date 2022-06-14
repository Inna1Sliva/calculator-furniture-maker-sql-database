package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.DB.DBHelper
import com.example.myapplication.Model.CartModel
import com.example.myapplication.Model.Listmodel
import java.util.jar.Manifest

class DataActivity : AppCompatActivity() {
   private lateinit var bKitchen:Button
   private lateinit var bMat:Button
   private lateinit var bClose:Button

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        initView()

        bKitchen.setOnClickListener {
            val i = Intent(this, DataKithen::class.java)
            startActivity(i)
        }



    }
    private fun initView() {
      bKitchen=findViewById(R.id.bKitchen)
        bClose=findViewById(R.id.bClose)
        bMat=findViewById(R.id.bMat)



    }
}

