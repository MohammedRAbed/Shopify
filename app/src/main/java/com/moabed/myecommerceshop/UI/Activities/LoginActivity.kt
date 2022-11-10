package com.moabed.myecommerceshop.UI.Activities

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.moabed.myecommerceshop.FireBaseFirestore.FireBaseFireStoreClass
import com.moabed.myecommerceshop.R
import com.moabed.myecommerceshop.Utils.Constants
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var email : String
    private lateinit var password : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onClick(view: View?) {
        if (view!=null) {
            when (view.id) {
                R.id.login_btn -> loginAuthentication()
                R.id.forgot_password_title -> startMyActivity(ForgotPasswordActivity())
                R.id.register_go_txt -> startMyActivity(RegisterActivity())
            }
        }
        et_email_auth.text.clear()
        et_password_auth.text.clear()
    }

    private fun validateLoginDetails() : Boolean {
        email = et_email_auth.text.toString().trim()
        password = et_password_auth.text.toString().trim()
        return if (!email.isEmailValid()) {
            showSnackBar("Enter you're email with @ format", true)
            failedEnterToEditText(et_email_auth)
            failedEnterToEditText(et_password_auth)

            false
        } else if (password.length<8) {
            showSnackBar("You're password's length is 8 at least",true)
            failedEnterToEditText(et_email_auth)
            failedEnterToEditText(et_password_auth)
            false
        } else{
            true
        }
    }

    private fun loginAuthentication() {
        if (validateLoginDetails()) {
            showProgressDialog("Please Wait")
            auth = Firebase.auth
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful)
                        FireBaseFireStoreClass().getUserDetails(this)
                    else
                        showSnackBar(task.exception!!.message!!,true)
            }
        }
    }

    fun loginUserSuccess(user:com.moabed.myecommerceshop.Models.User) {
        if (user.profileCompleted==0) {
            val intent = Intent(this, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_KEY,user)
            startActivity(intent)
        } else {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}