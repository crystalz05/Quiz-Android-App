package com.tyro.quizapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tyro.quizapplication.navigation.Navigation
import com.tyro.quizapplication.preferences_pk.setStatusBarIconColor
import com.tyro.quizapplication.screen.HomeScreen
import com.tyro.quizapplication.ui.theme.QuizApplicationTheme
import com.tyro.quizapplication.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val themeViewModel: ThemeViewModel by viewModels()

        setContent {
            val isDark by themeViewModel.isDarkTheme.collectAsState()

            SideEffect {
                setStatusBarIconColor(activity = this, darkIcons = !isDark)
            }

            QuizApplicationTheme(darkTheme = isDark) {
            Navigation(themeViewModel)
            }
        }
    }
}
