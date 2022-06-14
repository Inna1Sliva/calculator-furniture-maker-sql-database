package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ListAdapter

import com.example.myapplication.DB.DBHelper
import com.example.myapplication.DB.DBHelper.Companion.COLUMN_IMAGE
import com.example.myapplication.DB.DBHelper.Companion.COLUMN_PRODUCTID
import com.example.myapplication.DB.DBHelper.Companion.COLUMN_PRODUCTNAME
import com.example.myapplication.DB.DBHelper.Companion.COLUMN_PRODUCTPRICE
import com.example.myapplication.DB.DBHelper.Companion.PRODUCT_TABLE_NAME
import com.example.myapplication.Model.Listmodel
import com.example.myapplication.converter.DataConverter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.paperdb.Paper

class KitchenActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private  lateinit var fab:FloatingActionButton

    companion object {
        lateinit var dbhelper: DBHelper

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kitchen_kitchen)


        initView()
        initrecyclerView()
       fab.setOnClickListener {
      val i=Intent(this,CartListActivity::class.java)
            startActivity(i)

}


    }

    @SuppressLint("NotifyDataSetChanged", "Recycle", "Range")
    private fun initrecyclerView() {
        val db = dbhelper.readableDatabase
        val qry = ("SELECT * FROM ${PRODUCT_TABLE_NAME} ")
        val productlist = ArrayList<Listmodel>()
        val cursor = db.rawQuery(qry, null)
        if (cursor.count == 0){
            Toast.makeText(this, "Нет записей!!", Toast.LENGTH_SHORT).show() }else {
            while (cursor.moveToNext()) {
                val productID = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCTID))
                val productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTNAME))
                val productPrice = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTPRICE))
                val byteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
                val bitmap= DataConverter.getImage(byteArray)
                val productimage= Listmodel(productID,productName,productPrice, bitmap)
                productlist.add(productimage)
            }
            Toast.makeText(this, "${cursor.count.toString()} кол-во записей", Toast.LENGTH_SHORT)
                .show()
        }
        //val productlist = dbhelper.getProduct(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
       val adapter = ListAdapter(this,productlist)
        recyclerView.adapter = adapter
    }



    private fun initView() {
        Paper.init(this)
        recyclerView = findViewById(R.id.recyclerview)
       fab = findViewById(R.id.fab)
        dbhelper = DBHelper(this, null, null, 1)

    }

    override fun onResume() {
        initrecyclerView()
        super.onResume()
    }
}