package com.proxit.yamda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")
        val userIDView : TextView = findViewById(R.id.userId_main)
        val  emailIdView : TextView = findViewById(R.id.e_mail_Id_main)



        val logoutButton : Button = findViewById(R.id.logout_button)
        userIDView.text = userId
        emailIdView.text = emailId
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finish()
        }
    }
}