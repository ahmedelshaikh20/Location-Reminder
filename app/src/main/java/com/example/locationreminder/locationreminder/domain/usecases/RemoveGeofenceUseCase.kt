package com.example.locationreminder.locationreminder.domain.usecases

import com.example.locationreminder.locationreminder.data.service.GeofenceAppSource

class RemoveGeofenceUseCase(
    private val geofenceAppSource: GeofenceAppSource
) {
    fun execute(id: String) {
        geofenceAppSource.removeFeature(id)
    }
}
