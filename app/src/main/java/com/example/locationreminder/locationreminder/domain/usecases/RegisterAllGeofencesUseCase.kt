package com.example.locationreminder.locationreminder.domain.usecases

import com.example.locationreminder.locationreminder.data.service.GeofenceAppSource
import com.example.locationreminder.locationreminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.first

class RegisterAllGeofencesUseCase(
  private val reminderRepository: ReminderRepository,
  private val geofenceAppSource: GeofenceAppSource
) {
    suspend fun execute() {
        val activeReminders = reminderRepository.getAllActiveReminders().first()
      geofenceAppSource.removeAllFeatures()
        activeReminders.forEach{ reminder  ->
            geofenceAppSource.registerGeofence(reminder)
        } }
    }

