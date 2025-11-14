package com.example.locationreminder.locationreminder.presentation.mapscreen

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.locationreminder.locationreminder.presentation.common.ReminderInputContent
import com.example.locationreminder.locationreminder.presentation.service.LocationMonitoringService
import com.example.locationreminder.navigation.NavigationEvent
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.Marker
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@OptIn(MapboxExperimental::class)
@Composable
fun MapBoxScreen(viewmodel: MapBoxScreenViewmodel= koinViewModel(), navController: NavController, reminderId: Long?) {
  val localContext = LocalContext.current
  val state by viewmodel.loadedState.collectAsStateWithLifecycle()
  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions()
  ) { permissionsList ->
    val isGranted = permissionsList.values.all { it }
    if (isGranted) {
    } else {
    }
  }
  LaunchedEffect(Unit) {
    if (isPermissionGranted(localContext, permissions)) {
      // Update your Location
    } else {
      launcher.launch(permissions.toTypedArray());
    }
  }

  LaunchedEffect(Unit) {
    viewmodel.eventFlow.collectLatest {
      when(it){
        NavigationEvent.Back -> {navController.popBackStack()}
      }
    }

  }

  MapBoxContent(state, onEventClicked = { viewmodel.onEvent(it) })

}




@OptIn(MapboxExperimental::class)
@Composable
fun MapBoxContent(state : MapBoxScreenState , onEventClicked: (MapBoxScreenEvents) -> Unit){
  val mapViewportState = rememberMapViewportState {
    setCameraOptions {
      zoom(9.0)
      pitch(0.0)
      bearing(0.0)
    }
  }
  Box(modifier = Modifier.fillMaxSize()){
    MapboxMap(
      Modifier.fillMaxSize(),
      mapViewportState = mapViewportState
    ) {
      state.currPoint?.let { point ->
        Marker(point = point,)
      }
      MapEffect(Unit) { mapView ->
        mapView.location.updateSettings {
          locationPuck = createDefault2DPuck(withBearing = true)
          enabled = true
          puckBearing = PuckBearing.COURSE
          puckBearingEnabled = true
        }
        mapViewportState.transitionToFollowPuckState()
        val mapClickListener = OnMapClickListener { clickPoint ->
          onEventClicked(MapBoxScreenEvents.OnClickOnMapLocation(clickPoint))
          true
        }
        mapView.mapboxMap.addOnMapClickListener(mapClickListener)

      }

    }

    Surface(modifier = Modifier.fillMaxWidth().padding(16.dp)
      .align(Alignment.BottomCenter).clip(RoundedCornerShape(10.dp)),
      shadowElevation = 8.dp,
      color = MaterialTheme.colorScheme.surface) {

      ReminderInputContent(state) {
        onEventClicked(it)
      }

    }


  }
}




val permissions = listOf(
  android.Manifest.permission.ACCESS_COARSE_LOCATION,
  android.Manifest.permission.ACCESS_FINE_LOCATION,
  android.Manifest.permission.FOREGROUND_SERVICE_LOCATION
)

fun isPermissionGranted(context: Context, permissions: List<String>): Boolean {
  return permissions.all {
    ContextCompat.checkSelfPermission(
      context,
      it
    ) == PackageManager.PERMISSION_GRANTED
  }
}


