package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CartlistAdapter
import com.example.myapplication.DB.DBHelper
import io.paperdb.Paper


class CartListActivity : AppCompatActivity() {
    // private lateinit var itemsInTheCartModel:MutableList<CartModel>

    //private  var menuList:MutableList<CartModel> = mutableListOf()
    private lateinit var rv: RecyclerView

    //private var totalItemInCartCount =0
    lateinit var Ttext: TextView
    lateinit var dbhelper: DBHelper
    lateinit var adapter: CartlistAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        initView()
        initrecyclerView()
        // shoppCoast()


    }

    private fun initrecyclerView() {
        adapter = CartlistAdapter(this, ShoppingCart.getCart()!!)
        rv.adapter?.notifyDataSetChanged()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
        var totalPrice = ShoppingCart.getCart()!!
            .fold(0.toDouble()) { acc, cartModel -> acc + cartModel.amount.times(cartModel.product.productPrice!!.toDouble()) }
        Ttext.text = "${totalPrice} руб."
    }


    private fun initView() {
        Paper.init(this)
        Ttext = findViewById(R.id.tTitle)
        rv = findViewById(R.id.recyclerview)
        dbhelper = DBHelper(this, null, null, 1)
    }

    override fun onStart() {
        initrecyclerView()
        super.onStart()
    }
    override fun onResume() {
        initrecyclerView()
        super.onResume()
    }
}
