package com.example.chattingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Sign_up : AppCompatActivity() {

    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var edtName : EditText
    private lateinit var btnSignup : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mObRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtEmail =  findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtName  = findViewById(R.id.edt_name)
        btnSignup = findViewById(R.id.btnsignup)

        btnSignup.setOnClickListener {
            val name:String = edtName.text.toString()
            val email:String = edtEmail.text.toString()
            val password:String = edtPassword.text.toString()

            signUp(name,email,password)
        }
    }

    private fun signUp(name :String,email: String, password: String) {
       // logic for creating user

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                  
                    // Code for jumping to home
                    addUsertoDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@Sign_up,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    
                } else {
                    Log.d("TAG", "signUp: ${task.exception?.localizedMessage}")

                    Toast.makeText(this@Sign_up, "Some Error Occured", Toast.LENGTH_SHORT).show()
                }
            }
        
    }

    private fun addUsertoDatabase(name: String, email: String, uid: String) {
         mObRef = FirebaseDatabase.getInstance().getReference()

        mObRef.child("user").child(uid).setValue(User(name,email, uid))
    }
}