package com.eunji.lookatthis.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eunji.lookatthis.domain.GlobalNetworkResponseCodeFlow
import com.eunji.lookatthis.presentation.Constance.EXTRA_PAGE
import com.eunji.lookatthis.presentation.Constance.SIGN_IN
import com.eunji.lookatthis.presentation.Constance.SIGN_UP
import com.eunji.lookatthis.presentation.R
import com.eunji.lookatthis.presentation.databinding.ActivityMainBinding
import com.eunji.lookatthis.presentation.view.entry.EntryActivity
import com.eunji.lookatthis.presentation.view.links.LinkFragment
import com.eunji.lookatthis.presentation.view.sign_in.SignInFragment
import com.eunji.lookatthis.presentation.view.sign_up.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logout()
        if (savedInstanceState == null) {
            setPage()
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                GlobalNetworkResponseCodeFlow.isExpiredToken.collect { isExpiredToken ->
                    if (isExpiredToken) {
                        GlobalNetworkResponseCodeFlow.setGlobalResponseCodeFlow(false)
                        val intent = Intent(this@MainActivity, EntryActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setPage()
    }

    private fun setPage() {
        val extra = intent.getStringExtra(EXTRA_PAGE)
        if (extra == SIGN_UP) replaceFragment(SignUpFragment())
        else if (extra == SIGN_IN) replaceFragment(SignInFragment())
        else replaceFragment(LinkFragment())
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