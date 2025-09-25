package com.calyrsoft.ucbp1.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat

object NotificationHelper {
    const val DEFAULT_CHANNEL_ID = "default_channel"
    const val DEFAULT_CHANNEL_NAME = "Notificaciones generales"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager

            // Canal por defecto
            val defaultChannel = NotificationChannel(
                DEFAULT_CHANNEL_ID,
                DEFAULT_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones importantes de la aplicación"
                enableLights(true)
                enableVibration(true)
            }

            notificationManager.createNotificationChannel(defaultChannel)
            println("DEBUG: Canal de notificaciones creado: $DEFAULT_CHANNEL_ID")
        }
    }
}