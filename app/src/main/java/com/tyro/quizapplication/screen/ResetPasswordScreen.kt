package com.tyro.quizapplication.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.tyro.quizapplication.R
import com.tyro.quizapplication.navigation.Screen
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.QuizAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResetPasswordScreen(
    authViewModel: AuthViewModel,
    viewModel: QuizAppViewModel,
    navController: NavController
){
    val showPopup = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    val authResult by authViewModel.authResult.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(authResult) {
        authResult?.let {
            if(it.isSuccess){
                showPopup.value = true
            }else{
                val errorMessage2 = it.exceptionOrNull()?.message ?: "Unknown Error"
                snackbarHostState.showSnackbar(errorMessage2)
                authViewModel.clearAuthResult()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(title = {
                Text("Reset Password")},
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }

    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(imageVector = Icons.Default.Lock, contentDescription = "", tint = colorResource(id = R.color.blue_700), modifier = Modifier
                .size(64.dp)
                .background(color = colorResource(id = R.color.blue_200), shape = CircleShape)
                .padding(10.dp))
            Text("Reset your password", fontWeight = FontWeight.SemiBold, fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground)
            Text("Enter your email address and we'll send you a link to reset your password.",
                textAlign = TextAlign.Center, modifier = Modifier.width(300.dp), color = MaterialTheme.colorScheme.onBackground)

            Column(Modifier
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.background
                )
                .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.email,
                    onValueChange = {viewModel.email = it},
                    leadingIcon = {Icon(imageVector = Icons.Outlined.Email, contentDescription = "")},
                    placeholder = { Text("Email") }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    authViewModel.resetPassword(viewModel.email,
                        onSuccess = {showPopup.value = true},
                        onFailure = {e -> errorMessage.value = e.localizedMessage?.toString() ?: "Unknown Error"  })
                }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, color = Color.Transparent), content = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = "")
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Send email reset link")
                    })
            }

            TextButton(onClick = {navController.navigate(Screen.LoginScreen.route)}, content = {
                Text("Remember your password? Sign in")
            })

        }
        if(showPopup.value){
            PasswordResetPopup(closePopup = {showPopup.value = false}, message = errorMessage.value)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordResetPopup(
    closePopup : ()-> Unit,
    message: String
){

    val defaultMessage = "An email has been sent to email, check your email to proceed to rest " +
                "your password."

    BasicAlertDialog(onDismissRequest = {closePopup()}, properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.padding(32.dp)
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(imageVector = Icons.Filled.Email, contentDescription = "Email", Modifier.size(50.dp), tint = MaterialTheme.colorScheme.onSurface)

            Text(text = message.ifBlank { defaultMessage }, color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
            Button(onClick = {closePopup()}, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface)) {
                Text("Dismiss")
            }

        }

    }

}



@Preview(showBackground = true)
@Composable
fun ResetPasswordPreview(){
    PasswordResetPopup({},"")
}

