package com.codespacepro.notifications.notification

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import com.codespacepro.notifications.BaseApplication
import com.codespacepro.notifications.MainActivity
import com.codespacepro.notifications.R

@Composable
fun ShowNotifications() {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {

    }
    LaunchedEffect(key1 = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { createNotification(context) }) {
            Text(text = "Show Notification")
        }
    }
}

fun createNotification(context: Context) {
    val notificationManager = BaseApplication.notificationManager

    val notification = NotificationCompat.Builder(
        context, "Channel-ID"
    )
        .setContentTitle("Notification")
        .setContentText("This is Notification Content")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setAutoCancel(false)
        .addAction(R.drawable.ic_launcher_foreground, "Start", createPendingIntent(context))
        .setContentIntent(createPendingIntent(context))
        .build()

    notificationManager.notify(100, notification)
}

fun createPendingIntent(context: Context): PendingIntent {
    val flags = PendingIntent.FLAG_IMMUTABLE
    val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("data", "Hey, You just received a notification...")
    }

    return PendingIntent.getActivities(context, 100, arrayOf(intent), flags)
}