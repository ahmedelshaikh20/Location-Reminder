package com.example.locationreminder.navigation

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.locationreminder.locationreminder.presentation.mapscreen.MapBoxScreen
import com.example.locationreminder.locationreminder.presentation.reminderlist.ReminderListScreen
import com.example.locationreminder.locationreminder.presentation.service.LocationMonitoringService
import com.mapbox.android.core.permissions.PermissionsManager.Companion.areLocationPermissionsGranted

@Composable
fun AppNavigation() {
  val navController = rememberNavController()
  val context = LocalContext.current


  val launcher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
  ) { permissions ->
    if (permissions.values.all { it }) {
      startMonitoringService(context)
    }
  }

  LaunchedEffect(Unit) {
    if (areLocationPermissionsGranted(context)) {
      startMonitoringService(context)
    } else {
      launcher.launch(permissionsArray)
    }
  }
  NavHost(
    navController = navController,
    startDestination = ReminderListScreen,
  ) {
    composable<ReminderListScreen> {
      ReminderListScreen(navController = navController)
    }
    composable<MapBoxScreen> { backStackEntry ->
      val mapBoxScreen: MapBoxScreen = backStackEntry.toRoute()
      MapBoxScreen(navController =  navController, reminderId = mapBoxScreen.reminderId)
    }
  }



}

private fun startMonitoringService(context: Context) {
  val intent = Intent(context, LocationMonitoringService::class.java).apply {
    action = LocationMonitoringService.Actions.START.toString()
  }
  ContextCompat.startForegroundService(context, intent)
}

val permissionsArray = arrayOf(
  android.Manifest.permission.ACCESS_COARSE_LOCATION,
  android.Manifest.permission.ACCESS_FINE_LOCATION,
  android.Manifest.permission.FOREGROUND_SERVICE_LOCATION,
)
