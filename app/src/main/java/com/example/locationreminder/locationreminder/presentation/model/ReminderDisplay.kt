package com.example.locationreminder.locationreminder.presentation.model

import com.example.locationreminder.locationreminder.domain.model.Reminder
import com.mapbox.geojson.Point

enum class ReminderStatus {
  ACTIVE,
  PENDING,
  COMPLETED
}
data class ReminderDisplay(
  val id: Int?,
  val task: String,
  val locationString: String,
  val locationPoint: Point,
  val status: ReminderStatus,
  val isHighPriority: Boolean = false
)



fun ReminderDisplay.toDomainModel(): Reminder {
  return Reminder(
    task = task,
    location = locationString,
    longitude = locationPoint.longitude(),
    latitude = locationPoint.latitude(),
    isActive = if(status == ReminderStatus.ACTIVE) true else false,
    isHighPriority = isHighPriority
  )
}
