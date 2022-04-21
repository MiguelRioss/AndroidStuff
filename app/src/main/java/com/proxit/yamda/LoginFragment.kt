package com.proxit.yamda


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

private const val TAGG = "StatusActivity"

class LoginFragment : Fragment(), View.OnClickListener {
    private var emailInput: EditText? = null
    private var passwordInput: EditText? = null
    private var loginButton: Button? = null
    private var progressBar: ProgressBar? = null
    private var registerButton : TextView? = null

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        emailInput = view.findViewById(R.id.email_input)
        passwordInput = view.findViewById(R.id.password_input)
        loginButton = view.findViewById(R.id.submit_button)
        registerButton = view.findViewById(R.id.regist_button_text)
        progressBar = view.findViewById(R.id.progressBar)

        registerButton?.setOnClickListener(this)
        loginButton?.setOnClickListener(this)
        return view
    }

    @Override
    override fun onClick(view: View) {
        when (view.id) {
            R.id.regist_button_text -> startActivity(Intent(this.context,RegisterActivity::class.java))
            else -> instanceLogin()
        }
    }


    private fun instanceLogin() {
        val email: String = emailInput?.text.toString().trim { it <= ' ' }
        val password: String = passwordInput?.text.toString().trim { it <= ' ' }
        Log.d(TAGG, "Input inserted")
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            emailInput?.error = "Please enter E-amil"
            passwordInput?.error = "Plese enter your Password"
        }
        else if (TextUtils.isEmpty(email)) emailInput?.error = "Please enter E-amil"
        else if (TextUtils.isEmpty(password)) passwordInput?.error = "Plese enter your Password"
        else {
            progressBar?.visibility = View.VISIBLE
            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "You are logged Sucefully", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this.context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
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

                // Toast.makeText(this.context,toastMensage, Toast.LENGTH_SHORT).show()
            }
    }
}