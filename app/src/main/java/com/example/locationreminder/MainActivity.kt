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

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      enableEdgeToEdge()
        setContent {
            LocationReminderTheme {
                AppNavigation()
            }
        }
    }

}

