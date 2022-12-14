package com.moabed.myecommerceshop.UI.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.moabed.myecommerceshop.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        showText("Orders Fragment")


        return root
    }

    private fun showText(msg:String) {
        val textView: TextView = binding.textMyOrders
        textView.text = msg
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}