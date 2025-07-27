package com.example.carrentalnotification.domain.repository

import com.example.carrentalnotification.domain.model.CustomerConfig

interface SpeedRepository {
    fun getCustomerConfig(customerId: String): CustomerConfig
    fun notifyViolation(config: CustomerConfig, speed: Float)
}
