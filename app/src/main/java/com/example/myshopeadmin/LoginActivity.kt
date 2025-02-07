package com.example.myshopeadmin

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var email: EditText
    lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.email_login)
        password = findViewById(R.id.password_login)
        val SignUpPage = findViewById<TextView>(R.id.SignUpPage)
        SignUpPage.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        val login_btn = findViewById<AppCompatButton>(R.id.login_btn)
        login_btn.setOnClickListener {
            login()
        }

    }

    private fun login() {

        val email1 = email.text.toString()
        val password1 = password.text.toString()
        if (email1.isEmpty()) {
            email.error = "required this field"
        }
        if (password1.isEmpty()) {
            password.error = "required this field"
        }

        auth.signInWithEmailAndPassword(email1, password1)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Please Enter Your Right Email And Password",
                    Toast.LENGTH_SHORT
                ).show()
            }

        email.text.clear()
        password.text.clear()
    }
}