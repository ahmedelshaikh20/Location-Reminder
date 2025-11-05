package com.example.locationreminder.navigation// NavRoutes.kt or inside your main Activity file

object NavRoutes {
    const val REMINDER_ID_KEY = "reminderId"
    const val LIST_SCREEN = "list_screen"
    const val MAP_SCREEN_BASE = "map_box_screen"
    const val MAP_SCREEN_FULL = "$MAP_SCREEN_BASE/{$REMINDER_ID_KEY}"
    fun mapScreenPath(reminderId: Int): String {
        return "$MAP_SCREEN_BASE/$reminderId"
    }
}
