package com.example.idml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class Login : AppCompatActivity() {


    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide() //hide action bar


        //intilize the login text and button
        mAuth=FirebaseAuth.getInstance()
        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        btnLogin=findViewById(R.id.btnLogin)
        btnSignUp=findViewById(R.id.btnSignup)

        btnSignUp.setOnClickListener {
            val intent=Intent(this,SignUp::class.java)

            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()

            login(email,password);  // login called
        }



    }
      private fun login(email: String,password:String){
            // logic for logging user
          mAuth.signInWithEmailAndPassword(email, password)
              .addOnCompleteListener(this) { task ->
                  if (task.isSuccessful) {
                      // code for login in user
                      val intent=Intent(this,MainActivity::class.java)
                      finish()
                      startActivity(intent)
                  } else {
                      // If sign in fails, display a message to the user.
                       Toast.makeText(this@Login,"User does not exist..!",Toast.LENGTH_SHORT).show()
                  }
              }

      }


}