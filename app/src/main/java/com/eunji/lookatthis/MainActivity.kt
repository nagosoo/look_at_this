package com.eunji.lookatthis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eunji.lookatthis.databinding.ActivityMainBinding
import com.eunji.lookatthis.view.SignUpFragment
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPage()
        setAppBarTitle("Look At This")
    }

    private fun setPage() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SignUpFragment())
            .commit()
    }

     fun setAppBarTitle(title: String) {
        binding.appbar.tvTitle.text = title
    }

}

