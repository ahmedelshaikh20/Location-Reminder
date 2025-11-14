package com.example.locationreminder.locationreminder.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.locationreminder.locationreminder.data.model.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertReminder(reminder: ReminderEntity):Long
  @Query("SELECT * FROM reminders WHERE id = :reminderId")
  suspend fun getReminderById(reminderId: Int): ReminderEntity?

  @Query("UPDATE reminders SET isActive = :isActive WHERE id = :id")
  suspend fun updateStatus(id: Int, isActive: Boolean)

  @Query("SELECT * FROM reminders WHERE isActive = 1 ORDER BY id DESC")
  fun getAllActiveReminders(): Flow<List<ReminderEntity>>

  @Query("SELECT * FROM reminders ORDER BY id DESC")
  fun getAllReminders(): Flow<List<ReminderEntity>>



}
