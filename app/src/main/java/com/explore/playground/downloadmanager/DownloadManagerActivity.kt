package com.explore.playground.downloadmanager

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.explore.playground.R
import com.explore.playground.utils.toast
import kotlinx.android.synthetic.main.activity_download_manager.*
import kotlin.properties.Delegates

class DownloadManagerActivity : AppCompatActivity() {
    var downloadId by Delegates.notNull<Long>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        setContentView(R.layout.activity_download_manager)
        btDownloadFIle.setOnClickListener {
            val url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
            val fileName = "sample.pdf"

            val request = DownloadManager.Request(Uri.parse(url))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                .setDescription("description").setTitle(fileName)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true).setAllowedOverRoaming(false)
            (getSystemService(DOWNLOAD_SERVICE) as DownloadManager).apply {
                downloadId = enqueue(request)
            }
        }
    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            //Fetching the download id received with the broadcast
            val id: Long = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadId === id) {
                toast("Complete", Toast.LENGTH_SHORT)
            }
        }
    }
}