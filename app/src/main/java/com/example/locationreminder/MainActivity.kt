package com.example.locationreminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.locationreminder.locationreminder.presentation.di.appModule
import com.example.locationreminder.locationreminder.presentation.geofencing.GeofencingAppObserver
import com.example.locationreminder.locationreminder.presentation.reminderlist.ReminderListScreen
import com.example.locationreminder.navigation.AppNavigation
import com.example.locationreminder.ui.theme.LocationReminderTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
  val requestNotificationPermission =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
      if (isGranted) {
        Toast.makeText(this, "Notification permission granted ✅", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(this, "Notifications disabled ❌", Toast.LENGTH_SHORT).show()
      }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      askNotificationPermissionIfNeeded()

      enableEdgeToEdge()
        setContent {

            LocationReminderTheme {
                AppNavigation()
            }
        }
    }
  private fun askNotificationPermissionIfNeeded() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      val permission = Manifest.permission.POST_NOTIFICATIONS
      if (ContextCompat.checkSelfPermission(this, permission)
        != PackageManager.PERMISSION_GRANTED
      ) {
        requestNotificationPermission.launch(permission)
      }
    }
  }
}

