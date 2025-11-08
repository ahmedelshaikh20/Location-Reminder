package com.example.locationreminder.locationreminder.presentation.reminderlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.locationreminder.locationreminder.presentation.common.ReminderCard
import com.example.locationreminder.locationreminder.presentation.model.ReminderDisplay
import com.example.locationreminder.navigation.MapBoxScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun ReminderListScreen(
  viewmodel: ReminderListScreenViewmodel = koinViewModel(),
  navController: NavController
) {
  val state by viewmodel.loadedState.collectAsStateWithLifecycle()
  ReminderListContent(onFloatingPointClick = { navController.navigate(MapBoxScreen(reminderId = null)) },
    reminders = state.reminders)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderListContent(onFloatingPointClick: (Int?) -> Unit, reminders: List<ReminderDisplay>) {
  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 16.dp),
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Location Reminders",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
          )
        },
        windowInsets = WindowInsets.captionBar
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          onFloatingPointClick(null)
        },
        containerColor = MaterialTheme.colorScheme.primary
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Add New Reminder",
          tint = MaterialTheme.colorScheme.onPrimary
        )
      }
    }) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(reminders) { item ->
        ReminderCard(
          modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp
          ), reminder = item
        )
      }
    }
  }
}
