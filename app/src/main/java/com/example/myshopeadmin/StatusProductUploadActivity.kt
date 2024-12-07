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
import com.google.firebase.Timestamp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class StatusProductUploadActivity : AppCompatActivity() {
    lateinit var db: FirebaseDatabase
    lateinit var statusImageView: ImageView
    lateinit var statusTittleEditText: EditText
    lateinit var statusButton: Button
    var randomId = UUID.randomUUID().toString()
    var statusImageUri: Uri? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_status_product_upload)

        statusImageView = findViewById(R.id.statusUploadImageView)
        statusTittleEditText = findViewById(R.id.statusTittleEditText)
        statusButton = findViewById(R.id.statusUploadButton)

        statusImageView.setOnClickListener {
            selectImage()
        }
        statusButton.setOnClickListener {
            uploadStatusData()
        }

    }

    private fun selectImage() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            statusImageUri = data?.data!!
            statusImageView.setImageURI(statusImageUri)
        }
    }

    fun insertStatus(downloadUrl: String) {
        val statusTittle = statusTittleEditText.text.toString()
        val statusMap = hashMapOf(
            "statusId" to randomId,
            "statusTittle" to statusTittle,
            "statusImage" to downloadUrl
        )
        db = FirebaseDatabase.getInstance()
        db.getReference("users").child("status").child(randomId).setValue(statusMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Insert Data", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show()

            }

    }

    fun uploadStatusData() {

        statusImageUri.let { Uri ->

            val firebaseStorage = FirebaseStorage.getInstance().reference
            val imageName = Timestamp.now().nanoseconds.toString() + ".png"
            firebaseStorage.child("status$imageName").putFile(Uri!!)
                .addOnSuccessListener { task ->
                    task.storage.downloadUrl.addOnSuccessListener {
                        val downloadUrl = it.toString()
                        insertStatus(downloadUrl)
                        Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                        .addOnFailureListener {
                            Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show()
                        }

                }

        }


    }

}