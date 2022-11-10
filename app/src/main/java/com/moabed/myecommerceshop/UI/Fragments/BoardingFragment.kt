package com.moabed.myecommerceshop.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moabed.myecommerceshop.R
import com.moabed.myecommerceshop.UI.Activities.BoardingActivity
import com.moabed.myecommerceshop.Utils.Constants
import com.moabed.myecommerceshop.databinding.FragmentBoardingBinding
import kotlinx.android.synthetic.main.fragment_boarding.*
import kotlinx.android.synthetic.main.fragment_boarding.view.*

class BoardingFragment : Fragment() {

    private var _binding : FragmentBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoardingBinding.inflate(inflater, container, false)
        val root = binding.root

        switchWidgets()

        return root
    }

    private fun switchWidgets() {
        when(arguments?.getInt(Constants.BOARDING_ARGUMENT_KEY)) {
            0-> changeBoardData(
                R.drawable.ic_intro,
                R.string.boardTitle0,
                R.string.boardDes1
            )
            1-> changeBoardData(
                R.drawable.ic_scnd_ilstr,
                R.string.boardTitle2,
                R.string.boardDes2
            )
            2-> {
                changeBoardData(
                    R.drawable.ic_ilstr,
                    R.string.boardTitle3,
                    R.string.boardDes3
                )
                if (activity is BoardingActivity){
                    (activity as BoardingActivity).finishBoarding()
                }
            }
        }
    }

    private fun changeBoardData(imageId:Int,titleId:Int,desId:Int) {
        binding.boardingImg.setImageResource(imageId)
        binding.boardingTitle.text = resources.getText(titleId)
        binding.boardingDescription.text = resources.getText(desId)
    }
}