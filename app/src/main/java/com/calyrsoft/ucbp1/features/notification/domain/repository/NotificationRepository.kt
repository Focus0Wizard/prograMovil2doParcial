package com.calyrsoft.ucbp1.features.notification.domain.repository

interface NotificationRepository {
    fun generateToken(): String
}