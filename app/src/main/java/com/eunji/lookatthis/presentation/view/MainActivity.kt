package com.eunji.lookatthis.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.ActivityMainBinding
import com.eunji.lookatthis.presentation.Constance.EXTRA_PAGE
import com.eunji.lookatthis.presentation.Constance.SIGN_IN
import com.eunji.lookatthis.presentation.Constance.SIGN_UP
import com.eunji.lookatthis.presentation.view.links.LinkFragment
import com.eunji.lookatthis.presentation.view.sign_in.SignInFragment
import com.eunji.lookatthis.presentation.view.sign_up.SignUpFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            setPage()
        }
    }

    private fun setPage() {
        val extra = intent.getStringExtra(EXTRA_PAGE)
        extra?.let { extra ->
            if (extra == SIGN_UP) replaceFragment(SignUpFragment())
            else if (extra == SIGN_IN) replaceFragment(SignInFragment())
            else replaceFragment(LinkFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun setAppBarTitle(title: String) {
        binding.appbar.tvTitle.text = title
    }

}