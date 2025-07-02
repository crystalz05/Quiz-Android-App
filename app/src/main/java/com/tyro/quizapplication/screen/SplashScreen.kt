package com.tyro.quizapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import com.tyro.quizapplication.R
import com.tyro.quizapplication.auth.AuthState
import com.tyro.quizapplication.navigation.Screen
import com.tyro.quizapplication.viewmodel.AuthViewModel
import kotlinx.coroutines.tasks.await

@Composable
fun SplashScreen(authViewModel: AuthViewModel, onSplashFinished: (String) -> Unit) {

    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        authViewModel.checkAuthStatus()
        delay(1000L) // Give time for Firebase Auth + Network
        when (authState) {
            is AuthState.Verified -> {
                authViewModel.checkEmailVerification()
                authViewModel.fetchCurrentUser()
                onSplashFinished(Screen.HomeScreen.route)
            }
            else -> {
                onSplashFinished(Screen.LoginScreen.route)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun SplashScreenPreview(){
//    SplashScreen({})
//}
