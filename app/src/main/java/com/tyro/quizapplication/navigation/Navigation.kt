package com.tyro.quizapplication.navigation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.rpc.context.AttributeContext.Auth
import com.tyro.quizapplication.auth.AuthState
import com.tyro.quizapplication.screen.HistoryScreen
import com.tyro.quizapplication.screen.HomeScreen
import com.tyro.quizapplication.screen.LoginScreen
import com.tyro.quizapplication.screen.ProfileScreen
import com.tyro.quizapplication.screen.RegisterScreen
import com.tyro.quizapplication.screen.ResetPasswordScreen
import com.tyro.quizapplication.screen.SplashScreen
import com.tyro.quizapplication.screen.quizscreen.QuizCompletedScreen
import com.tyro.quizapplication.screen.quizscreen.QuizScreen
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.QuestionViewModel
import com.tyro.quizapplication.viewmodel.QuizAppViewModel
import com.tyro.quizapplication.viewmodel.QuizResultViewModel
import com.tyro.quizapplication.viewmodel.QuizViewModel
import com.tyro.quizapplication.viewmodel.ThemeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    authViewModel: AuthViewModel,
    themeViewModel: ThemeViewModel,
    viewModel: QuizAppViewModel = viewModel(),
    questionViewModel: QuestionViewModel = viewModel(),
    quizViewModel: QuizViewModel = viewModel(),
    quizResultViewModel: QuizResultViewModel = viewModel(),
    navHostController: NavHostController = rememberNavController()){

    val authState by authViewModel.authState.collectAsState()
    val hasNavigated by authViewModel.hasNavigated.collectAsState()

    LaunchedEffect(authState) {
        if (!hasNavigated) {
            when(authState) {
                AuthState.Verified, AuthState.Guest -> {
                    navHostController.navigate(Screen.HomeScreen.route){
                        popUpTo(0) {inclusive = true}
                    }
                    authViewModel.hasNavigated()
                }
                AuthState.Unverified, AuthState.LoggedOut -> {
                    navHostController.navigate(Screen.LoginScreen.route){
                        popUpTo(0){inclusive = true}
                    }
                    authViewModel.hasNavigated()
                }
                AuthState.Loading -> {
                    navHostController.navigate(Screen.SplashScreen.route){
                        popUpTo(0) {inclusive = true}
                    }
                    authViewModel.hasNavigated()
                }
            }
        }
    }

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
                historyScreen = {navHostController.navigate(Screen.HistoryScreen.route)},
                questionsViewModel = questionViewModel,
                quizResultViewModel = quizResultViewModel
            )
        }

        composable(Screen.ProfileScreen.route){
            ProfileScreen(
                themeViewModel = themeViewModel,
                navController = navHostController,
                authViewModel = authViewModel,
                quizResultViewModel = quizResultViewModel
            )
        }

        composable(Screen.RegisterScreen.route){
            RegisterScreen(
                viewModel,
                authViewModel,
                onNavToLogin = {
                    navHostController.navigate(Screen.LoginScreen.route)
                },
                navController = navHostController
            )
        }

        composable(Screen.HistoryScreen.route){
            HistoryScreen(
                authViewModel = authViewModel,
                quizResultViewModel = quizResultViewModel,
                navigateBack = {navHostController.navigateUp()}
            )
        }

        composable(Screen.ResetPasswordScreen.route){
            ResetPasswordScreen(authViewModel, viewModel, navHostController)
        }

        composable(Screen.QuizScreen.route) {
            QuizScreen(
                authViewModel = authViewModel,
                questionViewModel = questionViewModel,
                navController = navHostController,
                quizViewModel = quizViewModel,
                quizResultViewModel = quizResultViewModel
            )
        }

        composable(Screen.QuizCompletedScreen.route) {
            QuizCompletedScreen(
                questionsViewModel = questionViewModel,
                quizViewModel = quizViewModel,
                authViewModel = authViewModel,
                quizResultViewModel = quizResultViewModel,
                navController = navHostController)
        }

    }

}