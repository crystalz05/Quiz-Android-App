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
    primary = Color(0xFFFFD147),
    onPrimary = Color(0xFF000000),
    background = Color(0xFF0B1023),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF131A36),
    onSurface = Color(0xFFC2C7D6),
    secondary = Color(0xFF21294D),
    onSecondary = Color(0xFFFFFFFF),
    error = Color(0xFFB00020),
    outline = Color(0xFF2A3252),

)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0057FF),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFFF8F9FC),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    secondary = Color(0xFFF1F3F8),
    onSecondary = Color(0xFF000000),
    error = Color(0xFFB00020),
    onError = Color(0xFFFFFFFF),
    outline = Color(0xFFE4E7EE),
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