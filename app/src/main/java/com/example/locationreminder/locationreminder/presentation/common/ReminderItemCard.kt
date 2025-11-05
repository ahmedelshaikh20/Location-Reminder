package com.example.locationreminder.locationreminder.presentation.common


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.locationreminder.locationreminder.presentation.model.ReminderDisplay
import com.example.locationreminder.locationreminder.presentation.model.ReminderStatus
import com.example.locationreminder.ui.theme.AppTheme

@Composable
fun ReminderCard(reminder: ReminderDisplay, modifier: Modifier = Modifier) {
  val cardColor = when {
    reminder.status == ReminderStatus.PENDING -> AppTheme.colors.pendingCardBackground
    reminder.isHighPriority -> MaterialTheme.colorScheme.primaryContainer
    else -> MaterialTheme.colorScheme.surfaceContainerHigh
  }

  val statusDotColor = when (reminder.status) {
    ReminderStatus.ACTIVE -> AppTheme.colors.activeSuccess
    ReminderStatus.PENDING -> AppTheme.colors.pendingWarning
    ReminderStatus.COMPLETED -> AppTheme.colors.lowPriority
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp)),
    colors = CardDefaults.cardColors(containerColor = cardColor)
  ) {
    Row(
      modifier = Modifier
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = reminder.task,
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Near: ${reminder.locationString}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      Spacer(modifier = Modifier.width(16.dp))

      Box(
        modifier = Modifier.size(48.dp),
        contentAlignment = Alignment.Center
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          drawCircle(color = statusDotColor.copy(alpha = 0.1f))
        }
        Icon(
          imageVector = Icons.Sharp.Done,
          contentDescription = reminder.status.name,
          tint = statusDotColor,
          modifier = Modifier.size(24.dp)
        )
      }
    }
  }
}
//@Preview(showBackground = true)
//@Composable
//fun ReminderCardPreview() {
//  LocationReminderTheme {
//    Column(
//      modifier = Modifier
//        .fillMaxSize()
//        .padding(16.dp),
//      verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//      // Default Active Card (similar to "Buy groceries")
//      ReminderCard(
//        reminder = ReminderDisplay(1, "Buy groceries", "Supermart A", ReminderStatus.ACTIVE),
//      )
//
//      // High Priority Card (similar to "Pick up dry cleaning")
//      ReminderCard(
//        reminder = Reminder(2, "Pick up dry cleaning", "Laundry B", ReminderStatus.ACTIVE, isHighPriority = true),
//      )
//
//      // Pending/Warning Card (similar to "Call Mom")
//      ReminderCard(
//        reminder = Reminder(3, "Call Mom (library exit)", "City Library", ReminderStatus.PENDING),
//      )
//
//      // Completed Card
//      ReminderCard(
//        reminder = Reminder(4, "Schedule Doctor Appointment", "Clinic C", ReminderStatus.COMPLETED),
//      )
//    }
//  }
//}
