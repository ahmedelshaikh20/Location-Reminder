package com.example.locationreminder.locationreminder.data.service

import com.example.locationreminder.locationreminder.data.db.ReminderDao
import com.example.locationreminder.locationreminder.data.model.ReminderEntity
import kotlinx.coroutines.flow.Flow

class ReminderService(private val reminderDao: ReminderDao ) {

  fun getAllReminders(): Flow<List<ReminderEntity>> {
    return reminderDao.getAllReminders()
  }

  fun getActiveReminders(): Flow<List<ReminderEntity>> {
    return reminderDao.getAllActiveReminders()
  }

  suspend fun saveReminder(reminder: ReminderEntity):ReminderEntity{
    val id = reminderDao.insertReminder(reminder).toInt()
    return reminder.copy(id = id)
  }

  suspend fun getReminderById(id: Int): ReminderEntity? {
    return reminderDao.getReminderById(id)
  }

  suspend fun updateReminder(id: Int , status: Boolean) {
    reminderDao.updateStatus(id, status )
  }

}

