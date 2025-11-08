package com.example.locationreminder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.locationreminder.locationreminder.presentation.mapscreen.MapBoxScreen
import com.example.locationreminder.locationreminder.presentation.mapscreen.MapBoxScreenViewmodel
import com.example.locationreminder.locationreminder.presentation.reminderlist.ReminderListScreen
import com.example.locationreminder.locationreminder.presentation.reminderlist.ReminderListScreenViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation() {
  val navController = rememberNavController()
  NavHost(
    navController = navController,
    startDestination = ReminderListScreen,
  ) {
    composable<ReminderListScreen> {
      ReminderListScreen(navController = navController)
    }
    composable<MapBoxScreen> { backStackEntry ->
      val mapBoxScreen: MapBoxScreen = backStackEntry.toRoute()
      MapBoxScreen(navController =  navController, reminderId = mapBoxScreen.reminderId)
    }
  }

}
