package com.example.locationreminder.locationreminder.domain.repository

import com.example.locationreminder.locationreminder.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
  fun getAllReminders():Flow<List<Reminder>>
  suspend fun saveReminder(reminder: Reminder)
  suspend fun getAllActiveReminders():Flow<List<Reminder>>
  suspend fun getReminderById(id:Int):Reminder
}
