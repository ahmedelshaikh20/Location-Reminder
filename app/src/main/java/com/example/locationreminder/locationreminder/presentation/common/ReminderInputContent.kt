package com.example.locationreminder.locationreminder.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.locationreminder.locationreminder.presentation.mapscreen.MapBoxScreenEvents
import com.example.locationreminder.locationreminder.presentation.mapscreen.MapBoxScreenState

@Composable
fun ReminderInputContent(
  state: MapBoxScreenState,
  onEvent: (MapBoxScreenEvents) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "New Reminder",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.description,
            onValueChange = { onEvent(MapBoxScreenEvents.OnDescriptionChanged(it)) },
            label = { Text("New Reminder") },
            placeholder = { Text("e.g., Don't forget the dry cleaning!") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            singleLine = true
        )
        Button(
            onClick = { onEvent(MapBoxScreenEvents.OnSaveButtonClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(top = 16.dp)
        ) {
            Text(if (state.reminderId  == -1) "Save" else "Update")
        }

    }
}
