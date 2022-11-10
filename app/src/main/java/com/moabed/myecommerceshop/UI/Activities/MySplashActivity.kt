package com.moabed.myecommerceshop.UI.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.moabed.myecommerceshop.R
import kotlinx.android.synthetic.main.activity_my_splash.*

@SuppressLint("CustomSplashScreen")
class MySplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_splash)

        val handler = Handler(Looper.getMainLooper())

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        YoYo.with(Techniques.Bounce)
            .duration(1700)
            .repeat(2)
            .playOn(app_title)

        handler.postDelayed({
            val intent = Intent(this, BoardingActivity::class.java)
            startActivity(intent)
            finish()
        },3500)

    }
}