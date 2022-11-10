package com.moabed.myecommerceshop.UI.Activities

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.moabed.myecommerceshop.FireBaseFirestore.FireBaseFireStoreClass
import com.moabed.myecommerceshop.Models.User
import com.moabed.myecommerceshop.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var fName : String
    private lateinit var lName : String
    private lateinit var email : String
    private lateinit var password : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        setActionBar(register_user_toolbar)
        register_btn.setOnClickListener {
            registryAuthentication()
        }
    }

    private fun validateRegisterDetails() : Boolean {
        fName = et_first_name.text.toString().trim()
        lName = et_last_name.text.toString().trim()
        email = et_new_email.text.toString().trim()
        password = et_new_password.text.toString().trim()
        val passwordConf = et_password_confirm.text.toString().trim()

        return if(TextUtils.isEmpty(fName)) {
            showSnackBar("Please Enter your first name",true)
            false
        } else if (TextUtils.isEmpty(lName)){
            showSnackBar("Please Enter your last name",true)
            false
        } else if (!email.isEmailValid()) {
            showSnackBar("Email must be in @ format", true)
            false
        } else if (password.length < 8) {
            showSnackBar("You password's length must be at least 8", true)
            false
        } else if (password != passwordConf) {
            showSnackBar("Check that you're password has confirmed correctly", true)
            false
        } else if(!terms_conditions_check.isChecked) {
            showSnackBar("Agree on the Terms & Conditions",true)
            false
        } else {
            true
        }
    }

    private fun registryAuthentication() {
        auth = Firebase.auth
        if (validateRegisterDetails()) {
            showProgressDialog("Please Wait")
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        val user = User(auth.uid,fName,lName,email)
                        FireBaseFireStoreClass().registerUserDetails(this,user)
                    }
                    else
                        showSnackBar(task.exception!!.message!!,true)
                }
        }
    }

    fun registerUserSuccess() {
        Toast.makeText(this,
            "Successful Registry, you're Id is ${auth.uid}",
            Toast.LENGTH_LONG).show()
        auth.signOut()
        finish()
    }
}