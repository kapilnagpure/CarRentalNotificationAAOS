package com.example.carrentalnotification.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carrentalnotification.data.remote.AwsClient
import com.example.carrentalnotification.data.remote.FirebaseClient
import com.example.carrentalnotification.data.repository.SpeedRepositoryImpl
import com.example.carrentalnotification.domain.usecase.CheckSpeedUseCase

class SpeedViewModel : ViewModel() {
    // Wiring dependencies manually (can be replaced with Hilt/Dagger)
    val speedData = MutableLiveData("20")
   // var speedData = MutableLiveData("20")
    val customerIdData = MutableLiveData("customerId")
    private val firebaseClient = FirebaseClient()
    private val awsClient = AwsClient()
    private val repository = SpeedRepositoryImpl(firebaseClient, awsClient)
    private val useCase = CheckSpeedUseCase(repository)
    val speedWarning = MutableLiveData<Boolean>()

    fun onSpeedChanged(customerId: String, speed: Float) {
        val isOverSpeed = useCase.execute(customerId, speed)
        speedWarning.postValue(isOverSpeed)
        speedData.value = speed.toString()
        customerIdData.value = customerId
    }
}

