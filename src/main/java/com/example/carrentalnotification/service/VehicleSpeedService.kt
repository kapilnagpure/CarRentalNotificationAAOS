package com.example.carrentalnotification.service

import android.app.Service
import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder
import android.util.Log
import com.example.carrentalnotification.presentation.viewmodel.SpeedViewModel
import com.example.speedlimitdemo.utils.NotificationsUtility

class VehicleSpeedService : Service() {

    private lateinit var mCarPropertyManager: CarPropertyManager
    private var mCar: Car? = null
    private val mSpeedViewModel = SpeedViewModel()

    private val mSpeedCallback by lazy {
        object : CarPropertyManager.CarPropertyEventCallback {
            override fun onChangeEvent(value: CarPropertyValue<*>) {
                if (value.propertyId == VehiclePropertyIds.PERF_VEHICLE_SPEED) {
                    val speed = value.value as? Float ?: return
                    mSpeedViewModel.onSpeedChanged(speed)
                    Log.d("VehicleSpeed", "Current Speed: $speed")
                }
            }

            override fun onErrorEvent(propId: Int, area: Int) {
                Log.e("VehicleSpeed", "Error on property: $propId, area: $area")
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        initializeCarManager()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        mCarPropertyManager.registerCallback(
            mSpeedCallback,
            VehiclePropertyIds.PERF_VEHICLE_SPEED,
            CarPropertyManager.SENSOR_RATE_NORMAL
        )
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

    override fun onDestroy() {
        super.onDestroy()
        mCarPropertyManager.unregisterCallback(mSpeedCallback)
        mCar?.disconnect()
    }

    private fun initializeCarManager() {
        mCar = Car.createCar(this)
        mCarPropertyManager = mCar?.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
    }

}
