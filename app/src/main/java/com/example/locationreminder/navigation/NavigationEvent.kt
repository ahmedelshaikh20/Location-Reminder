package com.example.locationreminder.navigation

sealed class NavigationEvent {
  data object NavigateBack : NavigationEvent()
  data class NavigateToMapScreen(val reminderId: Int?) : NavigationEvent()
}
