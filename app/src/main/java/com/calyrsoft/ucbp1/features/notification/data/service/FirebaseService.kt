package com.calyrsoft.ucbp1.features.notification.data.service

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.calyrsoft.ucbp1.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.calyrsoft.ucbp1.core.NotificationHelper

class FirebaseService : FirebaseMessagingService() {
    companion object {
        val TAG = FirebaseService::class.java.simpleName
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)

        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        remoteMessage.notification?.let { notification ->
            Log.d(TAG, "Message Notification Body: " + notification.body)
            sendNotification(
                title = notification.title ?: "Nueva notificación",
                body = notification.body ?: "",
                data = remoteMessage.data
            )
        }
    }
    private fun sendNotification(title: String, body: String, data: Map<String, String>) {
        // Verificar permiso
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        val uniqueRequestCode = System.currentTimeMillis().toInt()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("NAVIGATE_TO", "GITHUB")
            putExtra("TIMESTAMP", System.currentTimeMillis())
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            uniqueRequestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // USAR EL CANAL CORRECTO
        val notificationBuilder = NotificationCompat.Builder(this, NotificationHelper.DEFAULT_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        try {
            with(NotificationManagerCompat.from(this)) {
                notify(uniqueRequestCode, notificationBuilder.build())
            }
            Log.d(TAG, "Notificación mostrada con código: $uniqueRequestCode")
        } catch (e: SecurityException) {
            Log.e(TAG, "Error de seguridad: ${e.message}")
        } catch (e: Exception) {
            Log.e(TAG, "Error al mostrar notificación: ${e.message}")
        }
    }
}