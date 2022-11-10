package com.moabed.myecommerceshop.UI.Activities

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.moabed.myecommerceshop.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        send_email_btn.setOnClickListener {
            sendResetPassword()
        }
        setActionBar(forgot_password_toolbar)
    }

    private fun sendResetPassword() {
        val email = et_send_email.text.toString().trim()
        if (email.isEmailValid()) {
            showProgressDialog("Please Wait")
            auth = Firebase.auth
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this) {task->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        Toast.makeText(this,"Email Sent!", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        showSnackBar(task.exception!!.message!!,true)
                    }
                    et_send_email.text.clear()
                }
        }
        else
            showSnackBar("Enter the email with @ format", true)

    }
}