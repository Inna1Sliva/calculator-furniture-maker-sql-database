package com.example.myapplication.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.KitchenActivity
import com.example.myapplication.Listener.IRecyclerClickListener
import com.example.myapplication.Model.CartModel
import com.example.myapplication.Model.Listmodel
import com.example.myapplication.R
import com.example.myapplication.ShoppingCart
import com.example.myapplication.converter.DataConverter
import kotlinx.android.synthetic.main.add_item.view.*
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter(var c:Context, val lmlist :List<Listmodel> = arrayListOf() ): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ListAdapter.ListViewHolder, position: Int) {
        var lm = lmlist[position]
        holder.bindingProducr(lm)
       val imageView=lm.productImage

        holder.product_image.setImageBitmap(imageView)
        holder.product.text = lm.productName
        holder.price.text = lm.productPrice.toString()

        holder.delet.setOnClickListener {
            val productName = lm.productName
            val popupMenu = PopupMenu(c, holder.delet)
            popupMenu.inflate(R.menu.show_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editText -> {
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item, null)
                        val product = v.findViewById<EditText>(R.id.edProduct)
                        val price = v.findViewById<EditText>(R.id.edPrice)
                        product.setText(lm.productName)
                        price.setText(lm.productPrice.toString())
                        val builder = AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton(
                                "Сохранить",
                                DialogInterface.OnClickListener { dialog, which ->
                                    val isUpdate = KitchenActivity.dbhelper.updateProduct(
                                        lm.productID.toString(),
                                        v.edProduct.text.toString(),
                                        v.edPrice.text.toString()
                                    )
                                    if (isUpdate == true) {
                                        lmlist[position].productName = v.edProduct.text.toString()
                                        lmlist[position].productPrice =
                                            v.edPrice.text.toString().toDouble().toString()
                                        notifyDataSetChanged()
                                        Toast.makeText(c, "Изменение сохранено", Toast.LENGTH_SHORT)
                                            .show()
                                    } else {
                                        Toast.makeText(c, "НЕ УДАЛОСЬ ИЗМЕНИТЬ", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }).setNegativeButton(
                                "Отмена",
                                DialogInterface.OnClickListener { dialog, which -> })
                        val alert = builder.create()
                        alert.show()

                        true
                    }
                    R.id.delet -> {
                        AlertDialog.Builder(c)
                            .setTitle("ВНИМАНИЕ")
                            .setMessage("Вы уверены, что хотите удалить: $productName?")
                            .setPositiveButton(
                                "Да",
                                DialogInterface.OnClickListener { dialog, which ->
                                    if (KitchenActivity.dbhelper.deletProduct(lm.productID!!)) {
                                      //lmlist.removeAt(position)
                                        notifyItemRemoved(position)
                                        notifyItemRangeChanged(position, lmlist.size)
                                        Toast.makeText(
                                            c,
                                            "Элемент $productName удален",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else
                                        Toast.makeText(c, "Ошибка", Toast.LENGTH_SHORT).show()
                                })
                            .setNegativeButton(
                                "Нет",
                                DialogInterface.OnClickListener { dialog, which -> })
                            .setIcon(R.drawable.ic_warning)
                            .show()
                        true
                    }
                    else -> true
                }
            }
            popupMenu.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
           // holder.setClickListener(object : IRecyclerClickListener {
              //override fun onItemClickListener(v: View?, position: Int) {
                 //   val listproduct = CartModel(Listmodel)
                 //   listproduct.product =lmlist[position].productName
                  //  listproduct.price = lmlist[position].productPrice
                  //  KitchenActivity.dbhelper.addShopp(c,listproduct)
              //  }
          //  })

    }

    override fun getItemCount(): Int {
        return lmlist.size
    }

 class ListViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

     // var id: TextView=view.findViewById(R.id.id)
     var product: TextView = view.product
     var price: TextView = view.price
     var product_image: ImageView = view.product_image
     var delet: ImageView = view.delet
     var seveCart: ImageView = view.seveCart





     private var clickListener: IRecyclerClickListener? = null
        fun setClickListener(clickListener: IRecyclerClickListener) {

            this.clickListener = clickListener

        }
     override fun onClick(p0: View?) {
            clickListener!!.onItemClickListener(p0, adapterPosition)
        }

     @SuppressLint("SetTextI18n")
     fun bindingProducr(listmodel: Listmodel) {
         //id.text=listmodel.productID.toString()
            val image= listmodel.productImage
         product_image.setImageBitmap(image)
         product.text=listmodel.productName
         price.text="${listmodel.productPrice} руб."




         seveCart.setOnClickListener { view->
             val item = CartModel(listmodel)
             ShoppingCart.addList(item)
          Toast.makeText(view.context, "${listmodel.productName}Добавлено в Лист рассчета",Toast.LENGTH_SHORT).show()
         }


     }

 }
}



