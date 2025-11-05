package com.example.locationreminder.locationreminder.presentation.mapscreen

import com.mapbox.geojson.Point

sealed class MapBoxScreenEvents {
  data class onClickOnMapLocation(val point: Point):MapBoxScreenEvents()
  data object onSaveButtonClick : MapBoxScreenEvents()
  data class onDescriptionChanged(val description: String) : MapBoxScreenEvents()
}
