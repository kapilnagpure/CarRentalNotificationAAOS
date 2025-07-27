package com.example.carrentalnotification.data.repository

import com.example.carrentalnotification.data.local.CustomerConfigProvider
import com.example.carrentalnotification.data.remote.AwsClient
import com.example.carrentalnotification.data.remote.FirebaseClient
import com.example.carrentalnotification.domain.model.CustomerConfig
import com.example.carrentalnotification.domain.repository.SpeedRepository
import com.example.carrentalnotification.utils.CommunicationChannel

class SpeedRepositoryImpl(
    private val firebaseClient: FirebaseClient,
    private val awsClient: AwsClient
) : SpeedRepository {

    override fun getCustomerConfig(customerId: String): CustomerConfig {
        return CustomerConfigProvider.getConfig(customerId)
    }

    override fun notifyViolation(config: CustomerConfig, speed: Float) {
        when (config.channel) {
            CommunicationChannel.FIREBASE -> firebaseClient.sendSpeedAlert(config.customerId, speed)
            CommunicationChannel.AWS -> awsClient.sendSpeedAlert(config.customerId, speed)
        }
    }
}