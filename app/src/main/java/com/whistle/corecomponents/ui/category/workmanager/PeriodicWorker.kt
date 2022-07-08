package com.whistle.corecomponents.ui.category.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.whistle.corecomponents.util.createNotification
import java.text.SimpleDateFormat
import java.util.*

class PeriodicWorker(private val appContext: Context, private val workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val params = workerParams.inputData.keyValueMap
        val title = params["title"] as String
        val date = Calendar.getInstance().time
        val simpleFormatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val content = "I am periodic worker at ${simpleFormatter.format(date)}"
        createNotification(appContext, title, content, true)
        return Result.success()
    }
}