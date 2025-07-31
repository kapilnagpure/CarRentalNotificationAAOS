package com.example.carrentalnotification.domain.repository

import com.example.carrentalnotification.domain.model.CustomerConfig

interface SpeedRepository {
    fun getCustomerConfig(): CustomerConfig
    fun notifyViolation(config: CustomerConfig, speed: Float)
}
