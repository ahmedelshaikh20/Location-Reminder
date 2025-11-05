package com.example.locationreminder.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
val LocalAppColors = staticCompositionLocalOf { LightAppColors }
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun LocationReminderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {// 1. Determine the standard M3 ColorScheme (light or dark)
val colorScheme = when {
  dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
    val context = LocalContext.current
    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
  }
  darkTheme -> darkColorScheme() // Use your custom darkColorScheme if no dynamic color
  else -> lightColorScheme()    // Use your custom lightColorScheme if no dynamic color
}

// 2. Determine your custom AppColors based on system theme
val appColors = if (darkTheme) DarkAppColors else LightAppColors

// 3. Provide the custom AppColors to the Composable tree
CompositionLocalProvider(LocalAppColors provides appColors) {
  // 4. Wrap everything in the standard MaterialTheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography, // Your M3 Typography
    content = content
  )
}
}

object AppTheme {
  val colors: AppColors
    @Composable
    get() = LocalAppColors.current
}
