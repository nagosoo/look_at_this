package com.eunji.lookatthis.presentation

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.eunji.lookatthis.R
import com.eunji.lookatthis.data.model.FcmTokenModel
import com.eunji.lookatthis.domain.usecase.alarm.PostFcmTokenUseCase
import com.eunji.lookatthis.domain.usecase.user.GetBasicTokenUseCase
import com.eunji.lookatthis.presentation.Constance.CHANNEL_DESCRIPTION
import com.eunji.lookatthis.presentation.Constance.CHANNEL_ID
import com.eunji.lookatthis.presentation.Constance.CHANNEL_NAME
import com.eunji.lookatthis.presentation.view.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var postFcmTokenUseCase: PostFcmTokenUseCase

    @Inject
    lateinit var getBasicTokenUseCase: GetBasicTokenUseCase
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Default)

    override fun onNewToken(token: String) {
        coroutineScope.launch {
            if (getBasicTokenUseCase().firstOrNull() != null)
                postFcmTokenUseCase(FcmTokenModel(fcmToken = token))
        }
        Log.d("Logging Fcm", "Refreshed fcm token: $token")
    }

    @SuppressLint("MissingPermission")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val mIntent = Intent(this, MainActivity::class.java).apply {
            //FLAG_ACTIVITY_NEW_TASK
            //이 액티비티가 실행될때 새로운 task를 만든다.
            //혹시 이미 이 액티비티기 있는 task가 있다면 그 task에서 실행됨
            //FLAG_ACTIVITY_CLEAR_TASK
            //task에서 이 액티비티 말고 다른 액티비티는 모두 제거함
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        createNotificationChannel()
        with(NotificationManagerCompat.from(this)) {
            notify(Random.nextInt(), builder.build())
        }
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            description = CHANNEL_DESCRIPTION
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

}