package com.tyro.quizapplication.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tyro.quizapplication.screen.HistoryScreen
import com.tyro.quizapplication.screen.HomeScreen
import com.tyro.quizapplication.screen.LoginScreen
import com.tyro.quizapplication.screen.ProfileScreen
import com.tyro.quizapplication.screen.RegisterScreen
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.QuizAppViewModel

@Composable
fun Navigation(viewModel: QuizAppViewModel = viewModel<QuizAppViewModel>(),
               authViewModel: AuthViewModel = viewModel<AuthViewModel>(),
               navHostController: NavHostController = rememberNavController()){

    NavHost(navController = navHostController,
        startDestination = Screen.LoginScreen.route,

        enterTransition = { fadeIn(animationSpec = tween(100)) },
        exitTransition = { fadeOut(animationSpec = tween(100)) },
        popEnterTransition = { fadeIn(animationSpec = tween(100)) },
        popExitTransition = { fadeOut(animationSpec = tween(100)) },

        ){

        composable(Screen.LoginScreen.route){
            LoginScreen(viewModel, onNavToRegister = {
                navHostController.navigate(Screen.RegisterScreen.route)
            })
        }

        composable(Screen.HomeScreen.route){
            HomeScreen()
        }

        composable(Screen.ProfileScreen.route){
            ProfileScreen()
        }

        composable(Screen.RegisterScreen.route){
            RegisterScreen(viewModel,
                authViewModel,
                onNavToLogin = {
                    navHostController.navigate(Screen.LoginScreen.route)
                }
            )
        }

        composable(Screen.HistoryScreen.route){
            HistoryScreen()
        }

    }

}