package com.example.locationreminder.locationreminder.domain.usecases

import com.example.locationreminder.locationreminder.domain.NotificationService
import com.example.locationreminder.locationreminder.domain.model.Reminder
import com.example.locationreminder.locationreminder.domain.repository.ReminderRepository

class HandleGeofenceTransitionUseCase(
  private val reminderRepository: ReminderRepository,
  private val notificationService: NotificationService
) {
  suspend fun execute(featureId: String) {
    val reminderId = featureId.toIntOrNull() ?: return
    val reminder: Reminder = reminderRepository.getReminderById(reminderId)
    val updatedReminder = reminder.copy(isActive = false)
    reminderRepository.saveReminder(updatedReminder)

    notificationService.showReminderNotification(reminder)
  }
}
