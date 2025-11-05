package com.example.locationreminder.locationreminder.presentation.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.locationreminder.R
import com.example.locationreminder.locationreminder.domain.NotificationService
import com.example.locationreminder.locationreminder.domain.model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NotificationServiceImpl(private val context: Context) : NotificationService {

  companion object {
    private const val CHANNEL_ID = "GEOFENCE_NOTIFICATIONS_ID"
  }

  private val notificationManager = NotificationManagerCompat.from(context)

  @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
  override suspend fun showReminderNotification(reminder: Reminder) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        CHANNEL_ID,
        "Location Reminders",
        NotificationManager.IMPORTANCE_HIGH
      ).apply {
        description = "Notifications for location-based reminders"
      }

      val sysManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      sysManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_launcher_foreground) // use a proper icon
      .setContentTitle("Reminder: ${reminder.location ?: "Location"}")
      .setContentText(reminder.task)
      .setStyle(NotificationCompat.BigTextStyle().bigText(reminder.task))
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setAutoCancel(true)

    withContext(Dispatchers.Main) {
      notificationManager.notify(reminder.id, builder.build())
    }

    Log.d("NOTIFICATION", "Showing notification for: ${reminder.task}")
  }
}
