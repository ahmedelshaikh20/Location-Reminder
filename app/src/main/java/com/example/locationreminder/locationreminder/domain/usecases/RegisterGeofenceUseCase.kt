package com.example.locationreminder.locationreminder.domain.usecases

import android.util.Log
import com.example.locationreminder.locationreminder.data.service.GeofenceAppSource
import com.example.locationreminder.locationreminder.domain.model.Reminder

class RegisterGeofenceUseCase(
    private val geofenceAppSource: GeofenceAppSource
) {

    fun execute(reminder: Reminder) {
        geofenceAppSource.registerGeofence(reminder)
        Log.d("RegisterGeofenceUseCase", "Geofence registered for reminder: ${reminder.id}" )
    }
}
