package com.example.myshopeadmin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Timestamp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class WomenProductsUploadActivity : AppCompatActivity() {
    lateinit var db: FirebaseDatabase
    lateinit var womenImageView: ImageView
    lateinit var womenTittleEditText: EditText
    lateinit var womenDescriptionEditText: EditText
    lateinit var womenDescription2EditText: EditText
    lateinit var womenProductPrice3EditText: EditText
    var randomId = UUID.randomUUID().toString()
    var womenImageUri: Uri? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_women_products_upload)

        womenImageView = findViewById(R.id.productUploadImageView)
        womenTittleEditText = findViewById(R.id.productTittleEditText)
        womenDescriptionEditText = findViewById(R.id.productDescriptionEditText)
        womenDescription2EditText = findViewById(R.id.productDescription2EditText)
        womenProductPrice3EditText = findViewById(R.id.productDescription3EditText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        db = FirebaseDatabase.getInstance()

        womenImageView.setOnClickListener {
            selectedImage()
        }

        submitButton.setOnClickListener {
            uploadWomenData()
        }


    }

    private fun selectedImage() {
        val gallery= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)
    }

    fun insertWomenProductsData(downloadUrl: String) {
        val womenTittle = womenTittleEditText.text.toString()
        val womenDescription = womenDescriptionEditText.text.toString()
        val womenDescription2 = womenDescription2EditText.text.toString()
        val womenProductPrice = womenProductPrice3EditText.text.toString()
        val womenMap = hashMapOf(
            "womenId" to randomId,
            "womenTittle" to womenTittle,
            "womenDescription" to womenDescription,
            "womenDescription2" to womenDescription2,
            "womenProductPrice" to womenProductPrice,
            "womenImage" to downloadUrl
            )
        db.getReference("users").child("womenProducts").child(randomId).setValue(womenMap)

    }

    fun uploadWomenData() {

        womenImageUri.let { Uri ->
            val firebaseStorage = FirebaseStorage.getInstance().reference
            val womenImageName = Timestamp.now().nanoseconds.toString() + ".png"
            firebaseStorage.child("womenProducts$womenImageName").putFile(Uri!!)
                .addOnSuccessListener { task ->
                    task.storage.downloadUrl.addOnSuccessListener {
                        val downloadUrl = it.toString()
                        insertWomenProductsData(downloadUrl)
                        Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                        .addOnFailureListener {
                            Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show()
                        }
                }


        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            womenImageUri = data?.data!!
            womenImageView.setImageURI(womenImageUri)
        }
    }

}