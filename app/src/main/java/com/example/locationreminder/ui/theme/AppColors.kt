package com.example.locationreminder.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Data class to hold custom application-specific colors (Semantic Colors).
 */
data class AppColors(
    val highPriority: Color,
    val mediumPriority: Color,
    val lowPriority: Color,
    val pendingWarning: Color,
    val activeSuccess: Color,
    val pendingCardBackground: Color
)

// Define the default light-themed colors for your app
val LightAppColors = AppColors(
    highPriority = Color(0xFFE91E63),   // Pink 500 (For High Urgency)
    mediumPriority = Color(0xFF2196F3), // Blue 500 (For standard reminder)
    lowPriority = Color(0xFF9E9E9E),    // Grey 500 (For very low priority)
    pendingWarning = Color(0xFFFFA000), // Amber 700 (For Status text/dot)
    activeSuccess = Color(0xFF4CAF50),  // Green 500 (For Active status)
    pendingCardBackground = Color(0xFFFFF8E1) // Amber 50 (Light background for pending card)
)

// Define the default dark-themed colors for your app
val DarkAppColors = AppColors(
    highPriority = Color(0xFFFF80AB),   // Pink A100
    mediumPriority = Color(0xFF82B1FF),  // Blue A100
    lowPriority = Color(0xFFBDBDBD),     // Grey 400
    pendingWarning = Color(0xFFFFC107),  // Amber 500
    activeSuccess = Color(0xFF81C784),   // Green 300
    pendingCardBackground = Color(0xFF42381F) // Darker background for pending card
)
