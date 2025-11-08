package com.example.locationreminder.navigation

import kotlinx.serialization.Serializable

@Serializable
object ReminderListScreen

@Serializable
data class MapBoxScreen(val reminderId: Long?)

