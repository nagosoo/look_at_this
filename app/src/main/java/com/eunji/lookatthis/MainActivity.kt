package com.eunji.lookatthis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.eunji.lookatthis.databinding.ActivityMainBinding
import com.eunji.lookatthis.view.LinkRegisterFragment
import com.eunji.lookatthis.view.SignInFragment
import com.eunji.lookatthis.view.SignUpFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPage()
    }

    private fun setPage() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LinkRegisterFragment())
            .commit()
    }

    fun setAppBarTitle(title: String) {
        binding.appbar.tvTitle.text = title
    }
}