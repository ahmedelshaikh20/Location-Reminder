package com.example.locationreminder.locationreminder.domain.usecases

import com.example.locationreminder.locationreminder.domain.model.Reminder
import com.example.locationreminder.locationreminder.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow

class GetAllRemindersUseCase (private val repository: ReminderRepository) {
   fun execute(): Flow<List<Reminder>> {
    return repository.getAllReminders()
  }
}
