package com.example.myapplication.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.myapplication.Model.CartModel
import com.example.myapplication.Model.Listmodel
import com.example.myapplication.R
import com.example.myapplication.ShoppingCart
import com.example.myapplication.converter.DataConverter

import kotlinx.android.synthetic.main.cart_list.view.*
import kotlinx.android.synthetic.main.cart_list.view.delet
import kotlinx.android.synthetic.main.cart_list.view.product_image
import kotlinx.android.synthetic.main.item_list.view.*
import java.io.ByteArrayOutputStream

class CartlistAdapter(var context: Context,
    var listCart:MutableList<CartModel>
):RecyclerView.Adapter<CartlistAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val tProduct:TextView=view.tProduct
            val tPrice:TextView=view.tPrice
             val quantity:TextView = view.quantity
             val delet: ImageView=view.delet
          var product_image:ImageView=view.product_image

          //  val imageMinus:ImageButton=view.imageMinus
           // val imagePlus:ImageButton=view.imagePlus


            fun Bind(cartmodel: CartModel) {
                //val bitmap = (product_image.drawable as BitmapDrawable).bitmap
                //bitmap.=cartmodel.product.productImage




                tProduct.text = cartmodel.product.productName
                tPrice.text ="${cartmodel.product.productPrice} руб"
                quantity.text =cartmodel.amount.toString()
                delet.setOnClickListener {
                      val item = CartModel(Listmodel())
                      ShoppingCart.deletCart(item)
                   }

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_list, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val lm =listCart[position]
        holder.Bind(lm)


    }
    override fun getItemCount(): Int {
        return listCart.size
    }


}