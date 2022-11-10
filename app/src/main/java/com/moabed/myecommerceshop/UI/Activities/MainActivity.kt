package com.moabed.myecommerceshop.UI.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moabed.myecommerceshop.R
import com.moabed.myecommerceshop.Utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mSharedPreferences = getSharedPreferences(Constants.ESHOP_PREFERENCES,Context.MODE_PRIVATE)
        textView.text = mSharedPreferences.getString(Constants.USERNAME_KEY,"DEFAULT USER")
    }


}