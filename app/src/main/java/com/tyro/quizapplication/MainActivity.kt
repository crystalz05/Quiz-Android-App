package com.tyro.quizapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tyro.quizapplication.navigation.Navigation
import com.tyro.quizapplication.screen.HomeScreen
import com.tyro.quizapplication.ui.theme.QuizApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizApplicationTheme {
            Navigation()
            }
        }
    }
}
