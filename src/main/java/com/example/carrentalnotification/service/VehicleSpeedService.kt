package com.example.carrentalnotification.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder
import com.example.speedlimitdemo.utils.NotificationsUtility

class VehicleSpeedService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        NotificationsUtility.createNotificationChannel(applicationContext)
        val notification = NotificationsUtility.createForegroundNotification(applicationContext)
        startForeground(
            123,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
        )
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}
