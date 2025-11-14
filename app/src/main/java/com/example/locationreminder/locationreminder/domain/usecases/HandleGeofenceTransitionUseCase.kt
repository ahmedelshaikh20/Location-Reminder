package com.example.locationreminder.locationreminder.domain.usecases

import android.util.Log
import com.example.locationreminder.locationreminder.data.service.GeofenceAppSource
import com.example.locationreminder.locationreminder.domain.NotificationService
import com.example.locationreminder.locationreminder.domain.model.Reminder
import com.example.locationreminder.locationreminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.first

class HandleGeofenceTransitionUseCase(
  private val reminderRepository: ReminderRepository,
  private val geofenceSource: GeofenceAppSource,
  private val notificationService: NotificationService
) {
  suspend fun execute(featureId: String) {
    val reminderId = featureId.toIntOrNull() ?: return
    val reminder = reminderRepository.getReminderById(reminderId)
    if (!reminder.isActive) {
      geofenceSource.removeFeature(featureId)
      return
    }
    notificationService.showReminderNotification(reminder)

    reminderRepository.updateReminderStatus(reminderId, false)

    geofenceSource.removeFeature(featureId)

  }
}
