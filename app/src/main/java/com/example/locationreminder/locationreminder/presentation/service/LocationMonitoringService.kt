package com.example.locationreminder.locationreminder.presentation.service
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.locationreminder.locationreminder.domain.usecases.RegisterAllGeofencesUseCase
import com.example.locationreminder.locationreminder.presentation.geofencing.GeofencingAppObserver
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.geofencing.GeofencingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin


@OptIn(MapboxExperimental::class)
class LocationMonitoringService : Service() {

  companion object {
    var serviceStartTime: Long = 0L
  }
  private lateinit var fusedClient: FusedLocationProviderClient

  private val locationRequest = LocationRequest.Builder(
    Priority.PRIORITY_HIGH_ACCURACY,
    2000 // 2 seconds
  ).build()

  private val locationCallback = object : LocationCallback() {
    override fun onLocationResult(result: LocationResult) {
      super.onLocationResult(result)
      val loc = result.lastLocation ?: return
      // Mapbox GeofencingService listens to location updates automatically
      Log.d("LocationService", "GPS: ${loc.latitude}, ${loc.longitude}")
    }
  }

  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

  private val geofencingService: GeofencingService by lazy { getKoin().get() }
  private val geofencingObserver: GeofencingAppObserver by lazy { getKoin().get() }
  private val registerAllUseCase: RegisterAllGeofencesUseCase by lazy { getKoin().get() }

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onCreate() {
    super.onCreate()
    fusedClient = LocationServices.getFusedLocationProviderClient(this)

    startForegroundLocationUpdates()


    geofencingService.addObserver(geofencingObserver){
      result->
      if (!result.isError) {
        Log.d("LocationMonitoringService", "Observer added successfully")
      } else {
        Log.e("LocationMonitoringService", "Failed to add observer", Throwable(result.error?.message))
      }
    }
    scope.launch { registerAllUseCase.execute() }



  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

    when (intent?.action) {
      Actions.START.toString() -> {
        startForegroundServiceWithNotification()
      }
      Actions.STOP.toString() -> stopSelf()
    }

    return START_STICKY
  }
  private fun startForegroundLocationUpdates() {
    try {
      fusedClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
      )
    } catch (e: SecurityException) {
      Log.e("LocationService", "Missing location permissions", e)
    }
  }




  private fun startForegroundServiceWithNotification() {

    val notification = NotificationCompat.Builder(this, "LocationServiceChannel")
      .setContentTitle("Location Reminders Enabled")
      .setContentText("Monitoring your location for reminders")
      .setOngoing(true)
      .build()

    startForeground(1, notification)
  }

  override fun onDestroy() {
    super.onDestroy()
    geofencingService.removeObserver(geofencingObserver){
      result->
      if (!result.isError) {
        Log.d("LocationMonitoringService", "Observer removed successfully")
      } else {
        Log.e("LocationMonitoringService", "Failed to remove observer", Throwable(result.error?.message))
      }
    }
    scope.cancel()

  }

  enum class Actions {
    START, STOP
  }
}
