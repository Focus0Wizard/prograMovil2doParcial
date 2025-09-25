package com.calyrsoft.ucbp1.features.notification.data.repository
import com.calyrsoft.ucbp1.features.notification.domain.repository.NotificationRepository

class NotificationRepositoryImpl : NotificationRepository {
    override fun generateToken(): String {
        // Genera un token simple (puedes hacerlo más complejo si necesitas)
        return "NOTIF_TOKEN_${System.currentTimeMillis()}_${(1000..9999).random()}"
    }
}