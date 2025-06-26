package com.tyro.quizapplication.navigation

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tyro.quizapplication.screen.HistoryScreen
import com.tyro.quizapplication.screen.HomeScreen
import com.tyro.quizapplication.screen.LoginScreen
import com.tyro.quizapplication.screen.ProfileScreen
import com.tyro.quizapplication.screen.RegisterScreen
import com.tyro.quizapplication.screen.ResetPasswordScreen
import com.tyro.quizapplication.screen.SplashScreen
import com.tyro.quizapplication.screen.quizscreen.QuizScreen
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.QuizAppViewModel
import com.tyro.quizapplication.viewmodel.ThemeViewModel

@Composable
fun Navigation(
    themeViewModel: ThemeViewModel = viewModel(),
    viewModel: QuizAppViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    navHostController: NavHostController = rememberNavController()){

    NavHost(navController = navHostController,
        startDestination = Screen.SplashScreen.route,

        enterTransition = { fadeIn(animationSpec = tween(100)) },
        exitTransition = { fadeOut(animationSpec = tween(100)) },
        popEnterTransition = { fadeIn(animationSpec = tween(100)) },
        popExitTransition = { fadeOut(animationSpec = tween(100)) },

        ){

        composable(Screen.SplashScreen.route) {
            SplashScreen(
                authViewModel = authViewModel,
                onSplashFinished = { route ->
                    navHostController.navigate(route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                viewModel,
                navHostController,
                authViewModel,
                onNavToHomeScreen = {
                    navHostController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                },
                onNavToRegister = {
                    navHostController.navigate(Screen.RegisterScreen.route)
                }
            )
        }

        composable(Screen.HomeScreen.route){
            HomeScreen(
                navController = navHostController,
                authViewModel = authViewModel,
                profileScreen = {navHostController.navigate(Screen.ProfileScreen.route)},
                historyScreen = {navHostController.navigate(Screen.HistoryScreen.route)}
            )
        }

        composable(Screen.ProfileScreen.route){
            ProfileScreen(
                themeViewModel = themeViewModel,
                navController = navHostController,
                authViewModel = authViewModel
            )
        }

        composable(Screen.RegisterScreen.route){
            RegisterScreen(viewModel,
                authViewModel,
                onNavToHomeScreen = {navHostController.navigate(Screen.HomeScreen.route)},
                onNavToLogin = {
                    navHostController.navigate(Screen.LoginScreen.route)
                }
            )
        }

        composable(Screen.HistoryScreen.route){
            HistoryScreen(navigateBack = {navHostController.navigateUp()})
        }

        composable(Screen.ResetPasswordScreen.route){
            ResetPasswordScreen(authViewModel, viewModel, navHostController)
        }

        composable(Screen.QuizScreen.route) {
            QuizScreen()
        }

    }

}