package com.moabed.myecommerceshop.UI.Activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import com.moabed.myecommerceshop.R
import kotlinx.android.synthetic.main.activity_my_splash.*
import kotlinx.android.synthetic.main.progress_dialog.*
import java.util.regex.Pattern

open class BaseActivity : AppCompatActivity() {
    private lateinit var mProgressDialog : Dialog
    private var doubleBackToExitPressedOnce : Boolean = false

    fun setActionBar(ab: Toolbar) {
        setSupportActionBar(ab)

        val actionBar = supportActionBar
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_btn)
        }

        ab.setNavigationOnClickListener {
            finish()
        }
    }

    fun showSnackBar(message:String, errorMessage:Boolean) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content),
        Html.fromHtml("<font color=\"#ffffff\">$message</font>"),Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        if (errorMessage)
            snackbarView.setBackgroundColor(Color.parseColor("#CA0000"))
        else
            snackbarView.setBackgroundColor(Color.parseColor("#00CA21"))

        snackbar.show()
    }

    fun showProgressDialog(text:String) {
        mProgressDialog = Dialog(this)

        mProgressDialog.setContentView(R.layout.progress_dialog)
        mProgressDialog.progress_title.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun String?.isEmailValid(): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = this?.let { pattern.matcher(it) }
        return matcher!!.matches()
    }

    fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
        } else {
            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show()
            @Suppress("DEPRECATION")
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }

    fun failedEnterToEditText(view:View) {
        YoYo.with(Techniques.Shake)
            .duration(500)
            .repeat(1)
            .playOn(view)
    }

    fun startMyActivity(act:Activity) {
        val intent = Intent(this,act::class.java)
        startActivity(intent)
    }
}