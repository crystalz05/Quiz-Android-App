package com.tyro.quizapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFFBBDEFB),             // From light
    onPrimaryContainer = Color(0xFF0D47A1),           // From light

    secondary = Color(0xFFB0BEC5),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFCFD8DC),           // From light
    onSecondaryContainer = Color(0xFF263238),         // From light

    tertiary = Color(0xFF80DEEA),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFB2EBF2),            // From light
    onTertiaryContainer = Color(0xFF004D40),          // From light

    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),

)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF0D47A1),              // From dark
    onPrimaryContainer = Color(0xFFE3F2FD),           // From dark

    secondary = Color(0xFF455A64),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF263238),           // From dark
    onSecondaryContainer = Color(0xFFECEFF1),         // From dark

    tertiary = Color(0xFF0097A7),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF004D40),            // From dark
    onTertiaryContainer = Color(0xFFE0F7FA),          // From dark

    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF121212),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun QuizApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}