package com.moabed.myecommerceshop.UI.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.moabed.myecommerceshop.R
import com.moabed.myecommerceshop.Utils.Constants
import com.moabed.myecommerceshop.Utils.ViewPagerAdapter
import io.grpc.Context
import kotlinx.android.synthetic.main.activity_boarding.*

class BoardingActivity : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boarding)

        showBoard()
    }

    override fun onClick(view : View?) {
        if (view!=null) {
            when(view.id) {
                R.id.next_board_btn -> {
                    when (boarding_vp.currentItem) {
                        0 -> boarding_vp.currentItem = 1
                        1 -> boarding_vp.currentItem = 2
                        2 -> {
                            finishBoarding()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }

                    }
                }
                R.id.skip_press -> {
                    finishBoarding()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun showBoard() {
        if (isBoarding()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setupViewPager()
        }
    }

    private fun setupViewPager() {
        val vpAdapter = ViewPagerAdapter(supportFragmentManager)
        boarding_vp.adapter = vpAdapter
        dotsIndicator.attachTo(boarding_vp)
    }

    private fun isBoarding() : Boolean {
        val shPref = getSharedPreferences(Constants.BOARDING_SP_KEY, android.content.Context.MODE_PRIVATE)
        return shPref.getBoolean("FINISH",false)
    }

    fun finishBoarding() {
        val shPref = getSharedPreferences(Constants.BOARDING_SP_KEY,android.content.Context.MODE_PRIVATE)
        val editor = shPref.edit()
        editor.putBoolean("FINISH",true)
        editor.apply()
    }
}