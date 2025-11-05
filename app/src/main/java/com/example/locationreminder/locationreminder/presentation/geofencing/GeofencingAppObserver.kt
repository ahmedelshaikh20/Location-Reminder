
package com.example.locationreminder.locationreminder.presentation.geofencing

import android.util.Log
import com.example.locationreminder.locationreminder.domain.usecases.HandleGeofenceTransitionUseCase
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.geofencing.GeofencingError
import com.mapbox.common.geofencing.GeofencingEvent
import com.mapbox.common.geofencing.GeofencingObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@OptIn(MapboxExperimental::class)
class GeofencingAppObserver(private val handleTransitionUseCase: HandleGeofenceTransitionUseCase) : GeofencingObserver {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val TAG = "GeofenceObserver"

    override fun onEntry(event: GeofencingEvent) {
        val featureId = event.feature.id() ?: return

        Log.d(TAG, "onEntry() called with: feature id = $featureId")

        coroutineScope.launch {
            handleTransitionUseCase.execute(featureId)
        }
    }

    override fun onDwell(event: GeofencingEvent) {
        Log.d(TAG, "onDwell() called with: feature id = ${event.feature.id()}")
    }

    override fun onExit(event: GeofencingEvent) {
        val featureId = event.feature.id() ?: return
        Log.d(TAG, "onExit() called with: feature id = $featureId")
    }

    override fun onError(error: GeofencingError) {
        Log.e(TAG, "Geofencing Error: $error")
    }

    override fun onUserConsentChanged(isConsentGiven: Boolean) {
        Log.d(TAG, "User Consent Changed: $isConsentGiven")
    }
}
