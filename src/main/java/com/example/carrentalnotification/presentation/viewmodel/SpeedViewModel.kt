package com.example.carrentalnotification.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carrentalnotification.data.remote.AwsClient
import com.example.carrentalnotification.data.remote.FirebaseClient
import com.example.carrentalnotification.data.repository.SpeedRepositoryImpl
import com.example.carrentalnotification.domain.usecase.CheckSpeedUseCase

class SpeedViewModel : ViewModel() {
    // Wiring dependencies manually (can be replaced with Hilt/Dagger)

    private val mFirebaseClient = FirebaseClient()
    private val mAwsClient = AwsClient()
    private val mRepository = SpeedRepositoryImpl(mFirebaseClient, mAwsClient)
    private val mUseCase = CheckSpeedUseCase(mRepository)
    val mSpeedWarning = MutableLiveData<Boolean>()
    val mCustomerIdData = MutableLiveData(mRepository.getCustomerConfig().customerId)
    val mSpeedData = MutableLiveData("20")

    fun onSpeedChanged(speed: Float) {
        val isOverSpeed = mUseCase.execute(speed)
        mSpeedWarning.postValue(isOverSpeed)
        mSpeedData.value = speed.toString()
    }
}

