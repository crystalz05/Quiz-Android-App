package com.tyro.quizapplication.navigation

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object HistoryScreen: Screen("history_screen")
    object HomeScreen: Screen("home_screen")
    object ProfileScreen: Screen("profile_screen")
    object RegisterScreen: Screen("register_screen")
    object SplashScreen : Screen("splash")
    object ResetPasswordScreen: Screen("password_reset")
    object QuizScreen: Screen("quiz_screen")
    object QuizCompletedScreen: Screen("quiz_completed_screen")

}