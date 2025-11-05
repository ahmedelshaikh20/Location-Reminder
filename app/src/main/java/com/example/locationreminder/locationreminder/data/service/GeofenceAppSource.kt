package com.example.locationreminder.locationreminder.data.service

import android.util.Log
import com.example.locationreminder.locationreminder.domain.model.Reminder
import com.google.gson.JsonPrimitive
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.geofencing.GeofencingPropertiesKeys
import com.mapbox.common.geofencing.GeofencingService
import com.mapbox.geojson.Feature


@OptIn(MapboxExperimental::class)
class GeofenceAppSource (private val geofencingService: GeofencingService) {

  fun removeFeature(id: String){
    geofencingService.removeFeature(id) {result ->
        if(result.isError){
          Log.e("GeofenceAppSource", "Failed to add feature error: ${result.error}")
        }else {
          Log.e("GeofenceAppSource", "Success to add feature")
        }
      }

  }


  fun registerGeofence(reminder: Reminder) {
    val geoJson = createGeoJsonFeature(reminder)
    val feature = Feature.fromJson(geoJson)
    feature.addProperty(GeofencingPropertiesKeys.DWELL_TIME_KEY, JsonPrimitive(1))
    geofencingService.addFeature(feature){result ->
      if(result.isError){
        Log.e("GeofenceAppSource", "Failed to add feature error: ${result.error}")
      }else {
        Log.e("GeofenceAppSource", "Success to add feature : ${result.value}")
      }
    }
  }

  private fun createGeoJsonFeature(reminder: Reminder): String {
    return """
        {
          "type": "Feature",
          "id": "${reminder.id}",
          "properties": {
            "name": "${reminder.task}",
            "MBX_GEOFENCE_POINT_RADIUS": 300
          },
          "geometry": {
            "type": "Point",
            "coordinates": [${reminder.longitude}, ${reminder.latitude}]
          }
        }
        """
  }
}
