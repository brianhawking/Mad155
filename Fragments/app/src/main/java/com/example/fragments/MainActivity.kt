package com.example.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.fragments.fragments.Fragment1
import com.example.fragments.fragments.Fragment2

class MainActivity : AppCompatActivity(), Comm1 {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn1 = findViewById<Button>(R.id.button1)
        val btn2 = findViewById<Button>(R.id.button2)

        val frag1 = Fragment1()
        var frag2 = Fragment2()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, frag1)
            commit()
        }

        btn1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, frag1)
                addToBackStack(null)
                commit()
            }
        }

        btn2.setOnClickListener {
            val name = findViewById<EditText>(R.id.name)
            val email = findViewById<EditText>(R.id.email)
            frag2.arguments = passTheData(name.text.toString(), email.text.toString())
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, frag2)
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun passTheData(name: String, email: String): Bundle {
        val bundle = Bundle()
        bundle.putString("name", name)
        bundle.putString("email", email)

        return bundle
    }
}