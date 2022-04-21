package com.proxit.yamda

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragmenet : Fragment(), View.OnClickListener {
    private var registerName: EditText? = null
    private var registerButton: Button? = null
    private var registerPassword: TextView? = null
    private var registerEmail: EditText? = null
    private var loginTextButton: TextView? = null
    private lateinit var  rootNode : FirebaseDatabase
    private lateinit var dataBaseReference : DatabaseReference



    @Override
    override fun onCreateView(inflater : LayoutInflater ,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?) : View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        registerButton = view.findViewById(R.id.button_register)
        registerName= view.findViewById(R.id.register_input_name)
        registerPassword = view.findViewById(R.id.register_input_password)
        registerEmail = view.findViewById(R.id.register_input_email)
        loginTextButton= view.findViewById(R.id.Login_button_registerActivity)

        loginTextButton?.setOnClickListener(this)
        registerButton?.setOnClickListener(this)
        return view
    }

    @Override
    override fun onClick(view: View) {
        UpdateDataBase() // register sem nada estraga o database todo


        when (view.id) {
            R.id.Login_button_registerActivity -> startActivity(Intent(this.context,LoginActivity::class.java))
            else -> instanceCreateAccount()
        }
    }

    private fun UpdateDataBase() {
        val nameText = registerName?.text.toString().trim { it <= ' ' }
        val database =
            FirebaseDatabase.getInstance("https://loginappdemo-9fe67-default-rtdb.europe-west1.firebasedatabase.app/")
        database.getReference("users").child(nameText).setValue("done")
    }


    private fun instanceCreateAccount() {
        val emailText = registerEmail?.text.toString().trim { it <= ' ' }
        val passwordText = registerPassword?.text.toString().trim { it <= ' ' }
        val nameText = registerName?.text.toString().trim { it <= ' ' }

        if (TextUtils.isEmpty(emailText) &&
            TextUtils.isEmpty(passwordText)
        ) {
            registerEmail?.error = "Please enter your Email"
            registerPassword?.error = "Please enter your Password"
            registerName?.error = "Please enter your Name"
        } else if (TextUtils.isEmpty(emailText))
            registerEmail?.error = "Please enter your Email"
        else if (TextUtils.isEmpty(passwordText))
            registerPassword?.error = "Please enter your Password"
        else if (TextUtils.isEmpty(nameText))
            registerName?.error = "Please enter your Name"

        //
        else {
            createAccount(emailText, passwordText)

        }
    }

    fun putInDataBase(emailText: String, passwordText: String, nameText: String) {



    }


    private fun createAccount(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    Toast.makeText(
                        this.context, "You are registered sucessfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent =
                        Intent(this.context, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("user_id", firebaseUser.uid)
                    intent.putExtra("email_id", email)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this.context,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }
    }



}