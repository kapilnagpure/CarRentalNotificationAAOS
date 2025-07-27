package com.example.carrentalnotification.domain.model

import com.example.carrentalnotification.utils.CommunicationChannel

data class CustomerConfig(
    val customerId: String,
    val maxAllowedSpeed: Float,
    val channel: CommunicationChannel
)