package com.example.locationreminder.locationreminder.presentation.reminderlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locationreminder.locationreminder.domain.model.Reminder
import com.example.locationreminder.locationreminder.domain.model.toDisplayModel
import com.example.locationreminder.locationreminder.domain.usecases.GetAllRemindersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ReminderListScreenViewmodel(getAllRemindersUseCase: GetAllRemindersUseCase):ViewModel() {

  private val _loadedState = MutableStateFlow(ReminderListScreenState())
  val loadedState:StateFlow<ReminderListScreenState> = _loadedState.asStateFlow()


  init {
    viewModelScope.launch {
      val remindersFlow: Flow<List<Reminder>> = getAllRemindersUseCase.execute()
      remindersFlow.map { domainList ->
          domainList.map { it.toDisplayModel() }
        }.collect { displayList ->
          _loadedState.value = _loadedState.value.copy(reminders = displayList)
        }
    }
  }

  fun onEvent( event: ReminderListScreenEvents){
    when(event){
      ReminderListScreenEvents.OnAddReminderClick -> {

      }
    }

  }



}
