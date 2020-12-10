package com.unnatifinalyearproject.artlance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

private const val Tag= "Login"
class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null){
            goPostActivity()
        }
        btnLogin.setOnClickListener{
            btnLogin.isEnabled = false
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isBlank() || password.isBlank()){
                Toast.makeText(
                    this, "Email/Password cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
            }
            //Firebase aunthentication check

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                btnLogin.isEnabled = true
                if (task.isSuccessful){
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                    goPostActivity()
                } else{
                    Log.e(Tag, "signInWithEmail failed", task.exception)
                    Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun goPostActivity() {
        Log.i(Tag, "goPostActivity")
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
        finish()
    }
}