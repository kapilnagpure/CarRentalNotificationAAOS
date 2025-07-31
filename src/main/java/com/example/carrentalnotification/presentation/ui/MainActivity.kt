package com.example.carrentalnotification.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.carrentalnotification.R
import com.example.carrentalnotification.databinding.MainActivityBinding
import com.example.carrentalnotification.presentation.viewmodel.SpeedViewModel
import com.example.carrentalnotification.service.VehicleSpeedService
import com.example.speedlimitdemo.utils.NotificationsUtility

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: MainActivityBinding
    private val mSpeedViewModel: SpeedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        mBinding.viewModel = mSpeedViewModel
        mBinding.lifecycleOwner = this
        Intent(this, VehicleSpeedService::class.java).also {
            startService(it)
        }

        mSpeedViewModel.mSpeedWarning.observe(this) { isOverSpeed ->
            if (isOverSpeed) {
                NotificationsUtility.sendNotification(context = applicationContext)
                Toast.makeText(this, "Speed exceeded!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Intent(this, VehicleSpeedService::class.java).also {
            stopService(it)
        }
    }

}
