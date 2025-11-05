package com.example.locationreminder.locationreminder.presentation.mapscreen

import com.mapbox.geojson.Point

data class MapBoxScreenState(
  val reminderId : Int? = -1,
  val currPoint: Point?=null,
  val description : String="",
)
