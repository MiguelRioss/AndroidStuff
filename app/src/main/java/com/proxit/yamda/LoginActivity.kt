package com.proxit.yamda


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth


class LoginActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login_activity_layout)
        checkSavedLogin()

    }

    private fun checkSavedLogin() {
        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user_id", auth.currentUser!!.uid)
            intent.putExtra("email_id", auth.currentUser!!.email)
            startActivity(intent);
            finish();
        }
    }
}