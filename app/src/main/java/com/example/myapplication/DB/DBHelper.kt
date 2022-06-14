package com.example.myapplication.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.myapplication.Model.Listmodel
import com.example.myapplication.converter.DataConverter
import java.io.ByteArrayOutputStream

import kotlin.collections.ArrayList

class DBHelper (context : Context, name:String?, factory: SQLiteDatabase.CursorFactory?, version : Int):
    SQLiteOpenHelper(context,DATABASE_NAME, factory,DATABAS_VERSION) {
    companion object {
        private val DATABASE_NAME = "Product.db"
        private val DATABAS_VERSION = 1

        val PRODUCT_TABLE_NAME = "Product"
        val COLUMN_PRODUCTID = "productid"
        val COLUMN_PRODUCTNAME = "productname"
        val COLUMN_IMAGE="productimage"
        val COLUMN_PRODUCTPRICE = "productprice"

        val SHOPP_TABLE_NAME = "imageList"
        val SHOPP_PRODUCTID = "imageid"
        val SHOPP_IMAGE ="image"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_PRODUCT_TABLE = ("CREATE TABLE $PRODUCT_TABLE_NAME (" +
                "$COLUMN_PRODUCTID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_PRODUCTNAME TEXT," +
                "$COLUMN_IMAGE BLOB," +
                "$COLUMN_PRODUCTPRICE DOUBLE DEFAULT 0)")
        val CREATE_IMAGE_TABLE = ("CREATE TABLE $SHOPP_TABLE_NAME (" +
                "$SHOPP_PRODUCTID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$SHOPP_IMAGE INTEGER)")
        db?.execSQL(CREATE_PRODUCT_TABLE)
        db?.execSQL(CREATE_IMAGE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $PRODUCT_TABLE_NAME")

        onCreate(db)
    }
fun addProduct(c:Context, productName: String, productPrice: String, productImage:ByteArray){
    val db = this.writableDatabase
    val v = ContentValues()
    v.put(COLUMN_PRODUCTNAME, productName)
    v.put(COLUMN_PRODUCTPRICE, productPrice)
    v.put(COLUMN_IMAGE, productImage)

       val result = db.insert(PRODUCT_TABLE_NAME, null, v)
    if(result!=null) {
        Toast.makeText(c, "Данные записаны", Toast.LENGTH_SHORT).show()
    }else {
        Toast.makeText(c, "Ошибка", Toast.LENGTH_SHORT).show()
    }
    db.close()
}
   // fun addProduct(context: Context, product: Listmodel) {
       // val db = this.writableDatabase

       // val v = ContentValues()
        //v.put(COLUMN_PRODUCTNAME, product.productName)
       // v.put(COLUMN_PRODUCTPRICE, product.productPrice)
       // v.put(COLUMN_IMAGE, product.productImage)
        //try {
           // db.insert(PRODUCT_TABLE_NAME, null, v)
            //Toast.makeText(context, "Данные записаны", Toast.LENGTH_SHORT).show()
       // } catch (e: Exception) {
            //Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
       // }
       // db.close()
   // }

    @SuppressLint("Range")
    fun getProduct(mCtx: Context,): ArrayList<Listmodel> {
        val qry = ("SELECT * FROM $PRODUCT_TABLE_NAME ")
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val productlist = ArrayList<Listmodel>()
        if (cursor.count == 0){
            Toast.makeText(mCtx, "Нет записей!!", Toast.LENGTH_SHORT).show() }else{
            cursor.moveToFirst()
            while (cursor.isAfterLast) {
                //val product = Listmodel()
                val productID = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCTID))
                val productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTNAME))
                val productPrice = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTPRICE))
               val byteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
                val bitmap=DataConverter.getImage(byteArray)
                val productimage= Listmodel(productID,productName,productPrice, bitmap)
                productlist.add(productimage)
            }
            Toast.makeText(mCtx, "${cursor.count.toString()} кол-во записей", Toast.LENGTH_SHORT)
                .show()
        }
        cursor.close()
       db.close()
        return productlist
    }

    fun deletProduct(productID: Int): Boolean {
        val db = this.writableDatabase
        val delet = db.delete(PRODUCT_TABLE_NAME, COLUMN_PRODUCTID + "=?", arrayOf(productID.toString()))
                .toLong()
        
        db.close()
        return Integer.parseInt("$delet") != -1
    }

    fun updateProduct(id: String, productName: String, productPrice: String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        var result: Boolean = false
        cv.put(COLUMN_PRODUCTNAME, productName)
        cv.put(COLUMN_PRODUCTPRICE, productPrice.toDouble())
        try {
            db.update(PRODUCT_TABLE_NAME, cv, "$COLUMN_PRODUCTID = ?", arrayOf(id))
            result = true
        } catch (e: Exception) {
            result = false
        }
        return result
    }

    //fun addShopp(cAd: Context, product: CartModel) {
       // val v = ContentValues()
       // v.put(SHOPP_PRODUCTNAME, product.product)
       // v.put(SHOPP_PRODUCTPRICE, product.price)
       // val db = this.writableDatabase
        //try {
          //  db.insert(SHOPP_TABLE_NAME, null, v)
          //  Toast.makeText(cAd, "Добавлено в лист рассчета", Toast.LENGTH_SHORT).show()
       // } catch (e: Exception) {
          //  Toast.makeText(cAd, e.message, Toast.LENGTH_SHORT).show()
       // }
       // db.close()
    //}
    //@SuppressLint("Range")
  //  fun getShopp(mCtx: Context): MutableList<CartModel> {
       // val qry = ("SELECT * FROM $SHOPP_TABLE_NAME")
       // val db = this.readableDatabase
       // val cursor = db.rawQuery(qry, null)
       // val productlist :MutableList<CartModel> = mutableListOf()
        //if (cursor.count == 0)
         //   Toast.makeText(mCtx, "Нет записей!!", Toast.LENGTH_SHORT).show() else {
          //  while (cursor.moveToNext()) {
               // val product = CartModel()
                //product.id= cursor.getInt(cursor.getColumnIndex(SHOPP_PRODUCTID))
                //product.product = cursor.getString(cursor.getColumnIndex(SHOPP_PRODUCTNAME))
                //product.price = cursor.getDouble(cursor.getColumnIndex(SHOPP_PRODUCTPRICE))
               // productlist.add(product)
          //  }
          //  Toast.makeText(mCtx, "${cursor.count.toString()} кол-во записей", Toast.LENGTH_SHORT)
              //  .show()
        //}
       // cursor.close()
       // db.close()
       // return productlist
    //}
   // @SuppressLint("Range")
   // fun getCount(mCtx: Context):ArrayList<CartModel> {
      //  val qry = ("SELECT COUNT($SHOPP_PRODUCTPRICE) FROM $SHOPP_TABLE_NAME")
      ////  val db = this.readableDatabase
      //  val cursor = db.rawQuery(qry, null)
      //  val productlist = ArrayList<CartModel>()
      //  if (cursor.moveToNext()) {
      //      val product = CartModel()
       //     product.price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCTPRICE))
       //     productlist.add(product)
       // }
       // cursor.close()
      //  db.close()
       // return productlist
  //  }
    }

