package com.eunji.lookatthis.presentation.view.entry

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eunji.lookatthis.databinding.ActivityEntryBinding
import com.eunji.lookatthis.presentation.Constance.EXTRA_PAGE
import com.eunji.lookatthis.presentation.Constance.MAIN
import com.eunji.lookatthis.presentation.Constance.SIGN_IN
import com.eunji.lookatthis.presentation.Constance.SIGN_UP
import com.eunji.lookatthis.presentation.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding
    private val viewModel: EntryViewModel by viewModels()
    private var isDoneCheckToken = false
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "허용됨. 사용자가 알림 허용을 누르면 여기를 탑니다.")
            } else {
                Log.i("Permission: ", "거부됨. 사용자가 알림 거절을 누르면 여기를 탑니다.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            //true이면 splash가 계속 보임
            !isDoneCheckToken
        }
        checkToken()
        requestNotificationPermission()
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun requestNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("Permission: ", "권한 허용 되었습니다.")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                Log.i("Permission: ", "알림 권한은 머머머 때문에 필요합니다.")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }

            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }
        }
    }

    private fun checkToken() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val token = viewModel.getBasicToken()
                token?.let { goToMain() }
                isDoneCheckToken = true
            }
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_PAGE, MAIN)
        startActivity(intent)
    }

    private fun setOnClickListener() {
        val intent = Intent(this, MainActivity::class.java)
        binding.btnSignIn.setOnClickListener {
            intent.putExtra(EXTRA_PAGE, SIGN_IN)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener {
            intent.putExtra(EXTRA_PAGE, SIGN_UP)
            startActivity(intent)
        }
    }

}