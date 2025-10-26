package com.my.kizzy.feature_api

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ApiService : Service() {
    @Inject
    lateinit var apiManager: ApiManager

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        apiManager.startServer()
        return START_STICKY
    }

    override fun onDestroy() {
        apiManager.stopServer()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
