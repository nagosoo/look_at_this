package com.eunji.lookatthis.presentation

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.eunji.lookatthis.R
import com.eunji.lookatthis.databinding.ActivityMainBinding
import com.eunji.lookatthis.presentation.view.main.MainFragment
import com.eunji.lookatthis.presentation.view.sign_up.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestNotificationPermission()
        setPage()
    }

    private fun setPage() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SignUpFragment())
            .commit()
    }

    fun setAppBarTitle(title: String) {
        binding.appbar.tvTitle.text = title
    }

    private fun requestNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("Permission: ", "권한 허용 되었습니다.")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                POST_NOTIFICATIONS
            ) -> {
                Log.i("Permission: ", "알림 권한은 머머머 때문에 필요합니다.")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(
                        POST_NOTIFICATIONS
                    )
                }
            }

            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(
                        POST_NOTIFICATIONS
                    )
                }
            }
        }
    }
}