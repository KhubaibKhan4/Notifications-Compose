package com.codespacepro.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class BaseApplication : Application() {
    companion object {
        lateinit var notificationManager: NotificationManager
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "Channel-ID",
                "Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notification Description"
            notificationChannel.enableLights(true)
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 100)

            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

        } else {
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

    }
}