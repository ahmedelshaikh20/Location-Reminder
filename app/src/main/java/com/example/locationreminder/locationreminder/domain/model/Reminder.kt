package com.example.locationreminder.locationreminder.domain.model

import android.util.Log
import com.example.locationreminder.locationreminder.data.model.ReminderEntity
import com.example.locationreminder.locationreminder.presentation.model.ReminderDisplay
import com.example.locationreminder.locationreminder.presentation.model.ReminderStatus
import com.mapbox.geojson.Point

data class Reminder(
  val id: Int = 0,
  val task: String,
  val latitude: Double,
  val longitude: Double,
  val isActive: Boolean,
  val isHighPriority: Boolean,
  val location: String?
)


fun Reminder.toEntity(): ReminderEntity {
  return ReminderEntity(
    task = task,
    isActive = isActive,
    isHighPriority = isHighPriority,
    locationName = location,
    latitude = latitude,
    longitude = longitude
  )
}


fun Reminder.toDisplayModel(): ReminderDisplay {
  Log.d("Reminder", "toDisplayModel: $this)")
  return ReminderDisplay(
    id = id,
    task = task,
    locationString = location?:"UnKnown Location",
    locationPoint = Point.fromLngLat(longitude, latitude),
    status = if (isActive) ReminderStatus.ACTIVE else ReminderStatus.COMPLETED
  )


}
