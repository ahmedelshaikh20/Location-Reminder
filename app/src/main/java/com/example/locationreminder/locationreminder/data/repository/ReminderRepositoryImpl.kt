package com.example.locationreminder.locationreminder.data.repository

import android.util.Log
import com.example.locationreminder.locationreminder.data.model.toDomain
import com.example.locationreminder.locationreminder.data.service.GeofenceAppSource
import com.example.locationreminder.locationreminder.data.service.ReminderService
import com.example.locationreminder.locationreminder.domain.model.Reminder
import com.example.locationreminder.locationreminder.domain.model.toEntity
import com.example.locationreminder.locationreminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderRepositoryImpl(private val reminderService: ReminderService, private val geofenceApiSource: GeofenceAppSource) : ReminderRepository {
  override  fun getAllReminders(): Flow<List<Reminder>> {
    return reminderService.getAllReminders().map { reminderList -> reminderList.map { it.toDomain() }}
  }


  override suspend fun saveReminder(reminder: Reminder) {
    val savedEntity = reminderService.saveReminder(reminder.toEntity())
    geofenceApiSource.registerGeofence(savedEntity.toDomain())
    Log.d("ReminderRepositoryImpl", "Reminder saved: $savedEntity")
  }

  override suspend fun getAllActiveReminders(): Flow<List<Reminder>> {
    return reminderService.getActiveReminders().map { reminderList -> reminderList.map { it.toDomain() }}
  }

  override suspend fun getReminderById(id: Int): Reminder {
    return reminderService.getReminderById(id)?.toDomain() ?: throw Exception("Reminder not found")
  }

  override suspend fun updateReminderStatus(id: Int, isActive: Boolean) {
    reminderService.updateReminder(id, isActive)
  }



}
