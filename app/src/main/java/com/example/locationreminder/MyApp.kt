package com.example.locationreminder

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.locationreminder.locationreminder.presentation.di.appModule
import com.example.locationreminder.locationreminder.presentation.geofencing.GeofencingAppObserver
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.geofencing.GeofencingFactory
import com.mapbox.common.geofencing.GeofencingService
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
@OptIn(MapboxExperimental::class)
class MyApp : Application() {
  private val geofencingAppObserver: GeofencingAppObserver by inject()
  private val geofencingService: GeofencingService by inject()

  override fun onCreate() {
    startKoin {
      androidContext(applicationContext)
      modules(appModule)
    }
    registerGeofenceObserver()
    super.onCreate()
  }

  @OptIn(MapboxExperimental::class)
  private fun registerGeofenceObserver() {
    geofencingService.addObserver(geofencingAppObserver) { result ->
      if (result.isError) {
        Log.e("Geofencing", "Failed to add observer: ${result.error})")
      }
    }
  }



}


