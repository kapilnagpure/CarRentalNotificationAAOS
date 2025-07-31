package com.example.carrentalnotification.domain.usecase

import com.example.carrentalnotification.domain.repository.SpeedRepository

class CheckSpeedUseCase(private val repository: SpeedRepository) {
    fun execute(speed: Float): Boolean {
        val config = repository.getCustomerConfig()
        val isOverLimit = speed > config.maxAllowedSpeed
        if (isOverLimit) {
            repository.notifyViolation(config, speed)
        }
        return isOverLimit
    }
}
