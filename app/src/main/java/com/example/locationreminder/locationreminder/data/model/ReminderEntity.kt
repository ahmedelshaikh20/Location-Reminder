package com.example.locationreminder.locationreminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.locationreminder.locationreminder.domain.model.Reminder

@Entity(tableName = "reminders")
data class ReminderEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val task: String,
  val latitude: Double,
  val longitude: Double,
  val locationName: String? = null,
  val isActive: Boolean,
  val isHighPriority: Boolean
)


fun ReminderEntity.toDomain(): Reminder {
  return Reminder(
    task = task,
    id = id,
    latitude = latitude,
    longitude = longitude,
    isActive = isActive,
    isHighPriority = isHighPriority,
    location = locationName
  )
}



