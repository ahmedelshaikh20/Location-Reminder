package com.example.locationreminder.locationreminder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.locationreminder.locationreminder.data.model.ReminderEntity

@Database(entities = [ReminderEntity::class] , version = 1 )
abstract class ReminderDatabase():RoomDatabase() {
  abstract fun reminderDao(): ReminderDao
}
