package com.whistle.corecomponents.category.workmanager

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.work.*
import com.whistle.corecomponents.BaseFragment
import com.whistle.corecomponents.MainActivity
import com.whistle.corecomponents.databinding.FragmentWorkManagerBinding
import java.util.*
import java.util.concurrent.TimeUnit

class WorkManagerFragment : BaseFragment<FragmentWorkManagerBinding>(FragmentWorkManagerBinding::inflate) {

    private val workManager by lazy { WorkManager.getInstance(requireActivity()) }

    private val requestMultiplePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

    }

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresStorageNotLow(true)
        .setRequiresBatteryNotLow(true)
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setMainTitle("WorkManager", true)

        requestMultiplePermission.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))

        with(binding) {
            bOnetimeWork.setOnClickListener {
                binding.lavLoading.playAnimation()
                runOnetimeWork()
            }

            bPeriodicWork.setOnClickListener { runPeriodicWork() }
        }
    }

    private fun runOnetimeWork() {
        val imageWorker = OneTimeWorkRequestBuilder<ImageDownloadWorker>()
            .setConstraints(constraints)
            .addTag("imageWork")
            .build()
        workManager.enqueueUniqueWork(
            "oneTimeImageDownload",
            ExistingWorkPolicy.KEEP,
            imageWorker
        )
        observeWork(imageWorker.id)
    }

    private fun runPeriodicWork() {
        // Min repeat interval should be 15 mins and min flex time interval 5 mins
        val request = PeriodicWorkRequestBuilder<PeriodicWorker>(
            1,
            TimeUnit.DAYS,
            5,
            TimeUnit.MINUTES
        )
            .setInputData(workDataOf(
                "title" to "Sample of periodic work"
            ))
            .build()
        workManager.enqueueUniquePeriodicWork(
            "Period",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun observeWork(id: UUID) {
        workManager.getWorkInfoByIdLiveData(id)
            .observe(viewLifecycleOwner) { info ->
                if (info != null && info.state.isFinished) {
                    binding.lavLoading.cancelAnimation()
                    val uriResult = info.outputData.getString("IMAGE_URI")
                    if (uriResult != null) {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(uriResult.toUri(), "*/*")
                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                    }
                }
            }
    }
}