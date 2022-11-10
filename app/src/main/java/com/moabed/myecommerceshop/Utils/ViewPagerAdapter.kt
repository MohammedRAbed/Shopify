package com.moabed.myecommerceshop.Utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moabed.myecommerceshop.UI.Fragments.BoardingFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        val myFragment =  BoardingFragment()

        val args = Bundle()
        args.putInt(Constants.BOARDING_ARGUMENT_KEY,position)
        myFragment.arguments = args

        return myFragment
    }
}