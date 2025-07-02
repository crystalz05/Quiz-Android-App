package com.tyro.quizapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tyro.quizapplication.data.misc.QuizGraph
import com.tyro.quizapplication.data.misc.ThemeMode
import com.tyro.quizapplication.navigation.Navigation
import com.tyro.quizapplication.preferences_pk.setStatusBarIconColor
import com.tyro.quizapplication.screen.HomeScreen
import com.tyro.quizapplication.ui.theme.QuizApplicationTheme
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels() // <- Scoped to Activity

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val themeViewModel: ThemeViewModel by viewModels()
        setContent {
            val themeMode by themeViewModel.themeMode.collectAsState()
            val isSystemDark = isSystemInDarkTheme()

            val isDark = when (themeMode) {
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                ThemeMode.SYSTEM -> isSystemDark
            }

            SideEffect {
                setStatusBarIconColor(activity = this, darkIcons = !isDark)
            }

            QuizApplicationTheme(darkTheme = isDark) {
            Navigation(authViewModel, themeViewModel)
            }
        }
    }
}
