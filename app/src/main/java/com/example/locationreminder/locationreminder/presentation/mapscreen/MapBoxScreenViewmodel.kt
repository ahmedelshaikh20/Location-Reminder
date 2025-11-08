package com.example.locationreminder.locationreminder.presentation.mapscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locationreminder.locationreminder.domain.usecases.SaveReminderUseCase
import com.example.locationreminder.locationreminder.presentation.model.ReminderDisplay
import com.example.locationreminder.locationreminder.presentation.model.ReminderStatus
import com.example.locationreminder.locationreminder.presentation.model.toDomainModel
import com.mapbox.geojson.Point
import com.mapbox.search.autocomplete.PlaceAutocomplete
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MapBoxScreenViewmodel(val saveReminderUseCase: SaveReminderUseCase) : ViewModel()  {

  private val _loadedState = MutableStateFlow(MapBoxScreenState())
  val loadedState : StateFlow<MapBoxScreenState> = _loadedState.asStateFlow()



  init {
      _loadedState.value= MapBoxScreenState()
  }
  private val placeAutocomplete = PlaceAutocomplete.create()
  fun onEvent(event : MapBoxScreenEvents){
    when(event){
      is MapBoxScreenEvents.OnClickOnMapLocation -> {
        _loadedState.update { it.copy(currPoint = event.point) }
      }
      is MapBoxScreenEvents.OnDescriptionChanged -> {
        _loadedState.update {  it.copy(description = event.description) }
      }
      MapBoxScreenEvents.OnSaveButtonClick -> {
        viewModelScope.launch {

          val reminderDisplay: ReminderDisplay? = _loadedState.value.currPoint?.let { point ->
            Log.d("MapBoxScreenViewmodel", "Saving reminder at lat=${point.latitude()}, lon=${point.longitude()}")
            val locationPoint = Point.fromLngLat(point.longitude(), point.latitude())
              ReminderDisplay(
              id = _loadedState.value.reminderId,
              task = _loadedState.value.description,
              locationString = placeAutocomplete.reverse(locationPoint).value?.get(0)?.formattedAddress ?: "Unknown",
              locationPoint = locationPoint,
              status = ReminderStatus.ACTIVE,
            )
          }
          reminderDisplay?.let {
            saveReminderUseCase.execute(reminderDisplay.toDomainModel())
          }
        }
      }
    }
  }
}


