package com.example.fragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fragments.R

class Fragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_2, container, false)

        var name = arguments?.getString("name")
        val nameText = view.findViewById<TextView>(R.id.textReceived1)
        nameText.text = name

        var email = arguments?.getString("email")
        val emailText = view.findViewById<TextView>(R.id.textReceived2)
        emailText.text = email

        return view
    }
}