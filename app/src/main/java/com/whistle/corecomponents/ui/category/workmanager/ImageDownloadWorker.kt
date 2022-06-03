package com.whistle.corecomponents.ui.category.workmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.whistle.corecomponents.R
import com.whistle.corecomponents.util.getUriFromUrl
import kotlinx.coroutines.delay

class ImageDownloadWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        setForeground(createForegroundInfo())
        delay(10000)
        val savedUri = context.getUriFromUrl()
        return Result.success(workDataOf("IMAGE_URI" to savedUri.toString()))
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        val notification = NotificationCompat.Builder(applicationContext, "workDownload")
            .setContentTitle("Downloading Your Image")
            .setTicker("Downloading Your Image")
            .setSmallIcon(R.drawable.duck)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_delete, "Cancel Download", intent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notification, "workDownload")
        }

        return ForegroundInfo(1, notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(notificationBuilder: NotificationCompat.Builder, id: String) {
        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE)
        val channel = NotificationChannel(
            id,
            "WorkManagerApp",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "WorkManagerApp Notifications"
        notificationManager.createNotificationChannel(channel)
    }
}