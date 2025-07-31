package com.example.carrentalnotification.data.local

import com.example.carrentalnotification.domain.model.CustomerConfig
import com.example.carrentalnotification.utils.CommunicationChannel

object CustomerConfigProvider {
    private const val COSTUMER_ID = "Customer_Id_1"
    fun getConfig(): CustomerConfig {
        return CustomerConfig(
            customerId = COSTUMER_ID,
            maxAllowedSpeed = 27.0f,
            channel = CommunicationChannel.FIREBASE
        )
    }
}