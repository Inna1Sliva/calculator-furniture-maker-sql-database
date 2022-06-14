package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.util.Util
import com.example.myapplication.DB.DBHelper
import com.example.myapplication.DB.DBHelper.Companion.PRODUCT_TABLE_NAME
import com.example.myapplication.Model.Listmodel
import com.example.myapplication.converter.DataConverter
import java.io.ByteArrayOutputStream

class DataKithen : AppCompatActivity() {
    private lateinit var edProduct: EditText
    private lateinit var edPrice: EditText
    private lateinit var bSeve: Button
    private lateinit var bBeck: Button
    private lateinit var imageView: ImageView

    // private var imageUri: Uri?= null
    lateinit var dbhelper: DBHelper
    private val CAMERA_REQUEST_CODE = 100
    private val STOREGE_REQUEST_CODE = 101
    private val IMAGE_PICK_CAMERA_CODE = 102
    private val IMAGE_PICK_GALERY_CODE = 103
    private lateinit var cameraPermission: Array<String>
    private lateinit var storagePermission: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_kithen)
        initView()
        cameraPermission = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        storagePermission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)


        bSeve.setOnClickListener {addProduct() }
            bBeck.setOnClickListener {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
            imageView = findViewById(R.id.imageView)
            imageView.setOnClickListener {
                imagePickDialog()

            }
        }


    private fun imagePickDialog() {
        val option = arrayOf("Camera", "Gallery")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Image From")
        builder.setItems(option) { dialog, which ->
            if (which == 0) {
                if (!checkCameraPermission()) {
                    requestCameraPermission()

                } else {
                    pickFromCamera()
                }
            } else {
                if (!checkStoregePermission()) {
                    requestStoregePermission()
                } else {
                    pickFromGallery()
                }

            }
        }
        builder.show()

    }

    private fun pickFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/+"
        startActivityForResult(galleryIntent, IMAGE_PICK_GALERY_CODE)
    }

    private fun requestStoregePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STOREGE_REQUEST_CODE)
    }

    private fun checkStoregePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun pickFromCamera() {
       // val values = ContentValues()
      //  values.put(MediaStore.Images.Media.TITLE, "image Title")
       // values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description")
      // imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
       // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE)
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val resultsq = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        return result && resultsq
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.size >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    pickFromCamera()
                // val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                   // val storegAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    //if (cameraAccepted && storegAccepted) {
                      //  pickFromCamera()
                    } else {
                        Toast.makeText(
                            this,
                            "Camera and Storage permission are required",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
               // }
            }
            STOREGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val storegAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storegAccepted) {
                        pickFromGallery()
                    } else {
                        Toast.makeText(
                            this,
                            " Storage permission are required",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }

    }

   @Deprecated("Deprecated in Java")
   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if (resultCode == Activity.RESULT_OK) {
                if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                val images:Bitmap=data?.extras?.get("data")as Bitmap
                    imageView.setImageBitmap(images)
                }
else if (requestCode == IMAGE_PICK_GALERY_CODE && resultCode == Activity.RESULT_OK) {
                    val gallimage: Bitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(gallimage)
                }

          }
          super.onActivityResult(requestCode, resultCode, data)
    }





// private fun addShopp() {
//  if (edProduct.text.isEmpty()) {
//   Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
//  edProduct.requestFocus()
//  } else {
//  val product = CartModel()
// product.product = edProduct.text.toString()
// if (edPrice.text.isEmpty())
//     product.price = 0.0 else
//   product.price = edPrice.text.toString().toDouble()
// dbhelper.addShopp(this, product)
// clearEditText()
// edProduct.requestFocus()


// }
//}



private fun addProduct() {
    val bitmap = (imageView.drawable as BitmapDrawable).bitmap

    if (edProduct.text.isEmpty() && edPrice.text.isEmpty()) {
        Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show()
        edProduct.requestFocus()
    }

        // val product = Listmodel()
        // product.productName = edProduct.text.toString()

        // product.productPrice = 0.0.toString() else
        // product.productPrice = edPrice.text.toString().toDouble().toString()
        // product.productImage =DataConverter.getbytes(bitmap)

            dbhelper.addProduct(this, edProduct.text.toString(),
                edPrice.text.toString().toDouble().toString(),
                DataConverter.getbytes(bitmap)
            )
        clearEditText()

}

    private fun clearEditText() {
        edProduct.text.clear()
         edPrice.text.clear()
        imageView.setImageResource(R.drawable.ic_photo_camera)
}



private fun initView() {
        edProduct = findViewById(R.id.edProduct)
        edPrice = findViewById(R.id.edPrice)
        imageView = findViewById(R.id.imageView)
        bBeck = findViewById(R.id.bBeck)
        bSeve = findViewById(R.id.bSeve)
        dbhelper = DBHelper(this, null, null, 1)

    }

}

