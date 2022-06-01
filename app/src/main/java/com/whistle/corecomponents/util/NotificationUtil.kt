package com.whistle.corecomponents.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.whistle.corecomponents.R
import kotlin.random.Random

fun createNotification(
    context: Context,
    title: String,
    content: String,
    shouldSendAlso: Boolean = false,
    id: Int = Random.nextInt(1000)
): Notification {
    val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
        .setSmallIcon(R.drawable.duck)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(false)

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationManager.createNotificationChannel(NotificationChannel(
            "CHANNEL_ID",
            "Duck channel",
            NotificationManager.IMPORTANCE_DEFAULT
        ))
    }

    if (shouldSendAlso) {
        notificationManager.notify(id, builder.build())
    }

    return builder.build()
}