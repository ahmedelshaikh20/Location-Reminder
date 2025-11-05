package com.example.locationreminder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.locationreminder.locationreminder.presentation.mapscreen.MapBoxScreen
import com.example.locationreminder.locationreminder.presentation.mapscreen.MapBoxScreenViewmodel
import com.example.locationreminder.locationreminder.presentation.reminderlist.ReminderListScreen
import com.example.locationreminder.locationreminder.presentation.reminderlist.ReminderListScreenViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(mapBoxScreenViewmodel: MapBoxScreenViewmodel = koinViewModel(),
                  reminderListScreenViewmodel: ReminderListScreenViewmodel= koinViewModel()
                  ) {
  val navController = rememberNavController()
  NavHost(
    navController = navController,
    startDestination = NavRoutes.LIST_SCREEN,
  ) {

    composable(NavRoutes.LIST_SCREEN) {
      ReminderListScreen(reminderListScreenViewmodel,navController = navController)
    }

    composable(
      route = NavRoutes.MAP_SCREEN_BASE, arguments = listOf(
      navArgument(NavRoutes.REMINDER_ID_KEY) {
        type = NavType.IntType
        defaultValue = -1
      }
    )) { backStackEntry ->
      val reminderId = backStackEntry.arguments?.getInt(NavRoutes.REMINDER_ID_KEY)
      MapBoxScreen(mapBoxScreenViewmodel, navController, reminderId)

    }


  }

}
