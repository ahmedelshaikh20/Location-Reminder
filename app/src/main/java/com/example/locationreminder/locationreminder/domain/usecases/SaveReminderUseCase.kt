package com.example.locationreminder.locationreminder.domain.usecases

import com.example.locationreminder.locationreminder.domain.model.Reminder
import com.example.locationreminder.locationreminder.domain.repository.ReminderRepository

class SaveReminderUseCase(private val repository: ReminderRepository) {
  suspend fun execute(reminder: Reminder){
    repository.saveReminder(reminder)
  }
}
