package com.example.locationreminder.locationreminder.presentation.reminderlist

import com.example.locationreminder.locationreminder.presentation.model.ReminderDisplay

data class ReminderListScreenState(
  val reminders:List<ReminderDisplay> = emptyList()
)
