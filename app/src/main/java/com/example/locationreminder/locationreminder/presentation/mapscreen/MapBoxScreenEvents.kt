package com.example.locationreminder.locationreminder.presentation.mapscreen

import com.mapbox.geojson.Point

sealed class MapBoxScreenEvents {
  data class OnClickOnMapLocation(val point: Point):MapBoxScreenEvents()
  data object OnSaveButtonClick : MapBoxScreenEvents()
  data class OnDescriptionChanged(val description: String) : MapBoxScreenEvents()
}
