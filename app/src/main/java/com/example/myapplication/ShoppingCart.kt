package com.example.myapplication

import android.content.Context
import android.widget.Toast
import com.example.myapplication.Model.CartModel
import io.paperdb.Paper

class ShoppingCart {


    companion object{
        fun addList(cartmodel: CartModel) {
          val cart = ShoppingCart.getCart()
            val targetList=cart?.singleOrNull{ it.product.productID==cartmodel.product.productID }
            if (targetList == null) {
                cartmodel.amount++
                cart?.add(cartmodel)
            }else {
                targetList.amount++
            }
            if (cart != null) {
                ShoppingCart.seveCartList(cart)
            }

        }
        fun removeCart(cartmodel: CartModel, context: Context){
            val cart =ShoppingCart.getCart()
            val targetList = cart?.singleOrNull { it.product.productID==cartmodel.product.productID}
            if (targetList !=null){
                if (targetList.amount>0){
                    Toast.makeText(context,"Очень много!",Toast.LENGTH_SHORT).show()
                    targetList.amount--
                }else{
                    cart.remove(targetList)
                }
            }
            if (cart != null) {
                ShoppingCart.seveCartList(cart)
            }
        }
        fun deletCart(cartmodel: CartModel){
            val cart = ShoppingCart.getCart()
            val targetList= cart?.singleOrNull{ it.product.productID==cartmodel.product.productID}
            cart?.remove(targetList)
            if (cart !=null){
                ShoppingCart.seveCartList(cart)
            }
        }

        fun getCart(): MutableList<CartModel>? {
           return Paper.book().read("cart", mutableListOf())
        }
        fun seveCartList(cart:MutableList<CartModel> ){
            Paper.book().write("cart",cart)
        }
        fun getShoppingCartSize():Int{
            var cartSize = 0
            ShoppingCart.getCart()?.forEach {
                cartSize +=it.amount;
            }
            return cartSize
        }
    }
}