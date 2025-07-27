package com.example.carrentalnotification.presentation.ui

import android.Manifest
import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.carrentalnotification.R
import com.example.carrentalnotification.databinding.MainActivityBinding
import com.example.carrentalnotification.presentation.viewmodel.SpeedViewModel
import com.example.carrentalnotification.service.VehicleSpeedService
import com.example.speedlimitdemo.utils.NotificationsUtility

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val viewModel: SpeedViewModel by viewModels()
    private lateinit var carPropertyManager: CarPropertyManager
    private var car: Car? = null

    private val speedCallback = object : CarPropertyManager.CarPropertyEventCallback {
        @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
        override fun onChangeEvent(value: CarPropertyValue<*>) {
            if (value.propertyId == VehiclePropertyIds.PERF_VEHICLE_SPEED) {
                val speed = value.value as? Float ?: return
                viewModel.onSpeedChanged("customerId1", speed)
                Log.d("VehicleSpeed", "Current Speed: $speed")
            }
        }

        override fun onErrorEvent(propId: Int, area: Int) {
            Log.e("VehicleSpeed", "Error on property: $propId, area: $area")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.main_activity)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initializeCarManager()
        viewModel.speedWarning.observe(this) { isOverSpeed ->
            if (isOverSpeed) {
                NotificationsUtility.sendNotification(context = applicationContext)
                Toast.makeText(this, "Speed exceeded!", Toast.LENGTH_SHORT).show()
            }
        }
        Intent(this, VehicleSpeedService::class.java).also {
            startService(it)
        }
    }

    private fun initializeCarManager() {
        car = Car.createCar(this)
        carPropertyManager = car?.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
        carPropertyManager.registerCallback(
            speedCallback,
            VehiclePropertyIds.PERF_VEHICLE_SPEED,
            CarPropertyManager.SENSOR_RATE_NORMAL
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        carPropertyManager.unregisterCallback(speedCallback)
        car?.disconnect()

    }
}
