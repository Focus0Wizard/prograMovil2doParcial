package com.calyrsoft.ucbp1.features.notification.domain.usecase

import com.calyrsoft.ucbp1.features.notification.data.repository.NotificationRepository

class GetTokenUseCase(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(): String {
        return repository.getToken()
    }
}