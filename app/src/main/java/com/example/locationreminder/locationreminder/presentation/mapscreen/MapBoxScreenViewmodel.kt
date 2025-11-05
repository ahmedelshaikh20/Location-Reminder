package com.example.locationreminder.locationreminder.presentation.mapscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locationreminder.locationreminder.domain.usecases.SaveReminderUseCase
import com.example.locationreminder.locationreminder.presentation.model.ReminderDisplay
import com.example.locationreminder.locationreminder.presentation.model.ReminderStatus
import com.example.locationreminder.locationreminder.presentation.model.toDomainModel
import com.example.locationreminder.navigation.NavigationEvent
import com.mapbox.geojson.Point
import com.mapbox.search.autocomplete.PlaceAutocomplete
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class MapBoxScreenViewmodel(val saveReminderUseCase: SaveReminderUseCase) : ViewModel()  {

  private val _loadedState = MutableStateFlow(MapBoxScreenState())
  val loadedState : StateFlow<MapBoxScreenState> = _loadedState.asStateFlow()
  private val navigationEventChannel = Channel<NavigationEvent>(Channel.BUFFERED)
  val navigationEvents = navigationEventChannel.receiveAsFlow()


  init {
      _loadedState.value= MapBoxScreenState()
  }
  private val placeAutocomplete = PlaceAutocomplete.create()
  fun onEvent(event : MapBoxScreenEvents){
    when(event){
      is MapBoxScreenEvents.onClickOnMapLocation -> {
        _loadedState.value = _loadedState.value.copy(currPoint = event.point)
      }
      is MapBoxScreenEvents.onDescriptionChanged -> {
        _loadedState.value = _loadedState.value.copy(description = event.description)
      }
      MapBoxScreenEvents.onSaveButtonClick -> {
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
            navigationEventChannel.send(NavigationEvent.NavigateBack)
          }
        }
      }
    }
  }




}


