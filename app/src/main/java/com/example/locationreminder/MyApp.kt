package com.example.locationreminder

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
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
  private val geofencingService: GeofencingService by inject()

  override fun onCreate() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel("LocationServiceChannel", "Location Monitoring", NotificationManager.IMPORTANCE_LOW)
      val manager = getSystemService(NotificationManager::class.java)
      manager.createNotificationChannel(channel)
    }
    startKoin {
      androidContext(applicationContext)
      modules(appModule)
    }
    super.onCreate()
  }





}


