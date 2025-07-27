package com.example.carrentalnotification.data.local

import com.example.carrentalnotification.domain.model.CustomerConfig
import com.example.carrentalnotification.utils.CommunicationChannel

object CustomerConfigProvider {
    fun getConfig(customerId: String): CustomerConfig {
        return CustomerConfig(
            customerId = customerId,
            maxAllowedSpeed = 27.0f,
            channel = CommunicationChannel.FIREBASE
        )
    }
}