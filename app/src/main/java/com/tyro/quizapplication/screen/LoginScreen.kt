package com.tyro.quizapplication.screen

import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tyro.quizapplication.R
import com.tyro.quizapplication.navigation.Screen
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.QuizAppViewModel


@Composable
fun LoginScreen(viewModel: QuizAppViewModel,
                navController: NavController,
                authViewModel: AuthViewModel,
                onNavToRegister: () -> Unit){

    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val orientationActivity = context as? Activity
    val result by authViewModel.authResult.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val isLoading by authViewModel.isUserLoading.collectAsState()
    orientationActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    LaunchedEffect(result) {
        result?.let {
            if (it.isSuccess) {
                authViewModel.checkEmailVerification()
            } else {
                val msg = it.exceptionOrNull()?.message ?: "Unknown error"
                snackbarHostState.showSnackbar(msg)
                authViewModel.clearAuthResult()
            }
        }
    }

    val emailVerified by authViewModel.emailVerified.observeAsState()
    LaunchedEffect(emailVerified) {
        if (emailVerified?.isSuccess == true) {
            authViewModel.clearAuthResult()
            authViewModel.clearEmailVerified()
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.LoginScreen.route) { inclusive = true }
            }
        } else if (emailVerified != null && emailVerified?.isSuccess == false) {
            snackbarHostState.showSnackbar("Please verify your email first.")
            authViewModel.clearEmailVerified()
            authViewModel.logout()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {innerPadding ->

        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            ) {

            Column {
                Text("Enter your login credentials")
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.email,
                    maxLines = 1,
                    onValueChange = {viewModel.email = it},
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_person_24), contentDescription = "")},
                    placeholder = { Text("Email") }
                    )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.password,
                    maxLines = 1,
                    onValueChange = {viewModel.password = it},
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_no_encryption_24), contentDescription = "")},
                    placeholder = { Text("Password") },
                    visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {passwordVisible = !passwordVisible}) {
                            Icon(painter = if(passwordVisible) painterResource(id = R.drawable.baseline_visibility_off_24) else
                                painterResource(id = R.drawable.baseline_visibility_24)
                                ,
                                contentDescription = ""
                            )
                        }
                    }
                    )

                Spacer(modifier = Modifier.height(10.dp))
                TextButton(onClick = {
                    navController.navigate(Screen.ResetPasswordScreen.route)
                }, modifier = Modifier.fillMaxWidth(), content = {
                    Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Right, text = "Forgot Password")
                })
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    if(viewModel.email.isEmpty()||viewModel.password.isEmpty()){
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
                    }else{
                        authViewModel.resetHasNavigated()
                        authViewModel.logIn(viewModel.email.trim(), viewModel.password.trim())
                        viewModel.clearFields()
                    }

                }, shape = RoundedCornerShape(4.dp), modifier = Modifier.fillMaxWidth()) {
                    Text("Login")
                }
                TextButton(onClick = {onNavToRegister()})
                {
                    Text("Don't have account? Register here", color = MaterialTheme.colorScheme.onBackground)
                }
                Spacer(modifier = Modifier.height(30.dp))
                IconButton(onClick = {
                }, modifier = Modifier.size(60.dp).align(alignment = Alignment.CenterHorizontally)
                    ,  ) {

                    Icon(painter = painterResource(id = R.drawable.baseline_fingerprint_24), contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground

                        )
                }

                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = {
                    authViewModel.resetHasNavigated()
                    authViewModel.loginAsGuest()
                                 },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(4.dp), modifier = Modifier.fillMaxWidth()) {
                    Text("Continue as Guest", color = MaterialTheme.colorScheme.onSecondary)
                }

            }

        }
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

    }
}


//@SuppressLint("ViewModelConstructorInComposable")
//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview(){
//
//    LoginScreen(viewModel = QuizAppViewModel(), authViewModel = AuthViewModel(),{}, {})
//}