package com.example.madd_q_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var userEmail: EditText
    private lateinit var userName: EditText
    private lateinit var userPassword: EditText
    private lateinit var userConfPassword: EditText
    private lateinit var btnRegister: Button

    private lateinit var dbRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userEmail = findViewById(R.id.emailText)
        userName = findViewById(R.id.nameText)
        userPassword = findViewById(R.id.passwordText)
        userConfPassword = findViewById(R.id.confPasswordText)
        btnRegister = findViewById(R.id.regButton)


        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        btnRegister.setOnClickListener {
            saveUserData()
        }
    }

    private fun saveUserData(){

        //get values
        val usrEmail = userEmail.text.toString()
        val usrName = userName.text.toString()
        val usrPassword = userPassword.text.toString()
        val usrConfPassword = userConfPassword.text.toString()

        if (usrEmail.isEmpty()) {
            userEmail.error = "Please enter name"
        }
        if (usrName.isEmpty()) {
            userName.error = "Please enter age"
        }
        if (usrPassword.isEmpty()) {
            userPassword.error = "Please enter salary"
        }
        if (usrConfPassword.isEmpty()) {
            userConfPassword.error = "Please enter salary"
        }

        val usrId = dbRef.push().key!!

        val user = UserModel(usrId, usrEmail, usrName, usrPassword, usrConfPassword)

        dbRef.child(usrId).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this, "User Registered successfully", Toast.LENGTH_LONG).show()

                userEmail.text.clear()
                userName.text.clear()
                userPassword.text.clear()
                userConfPassword.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}
