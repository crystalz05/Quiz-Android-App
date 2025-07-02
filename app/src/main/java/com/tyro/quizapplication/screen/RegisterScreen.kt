package com.tyro.quizapplication.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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



@Composable
fun RegisterScreen(viewModel: QuizAppViewModel,
                   authViewModel: AuthViewModel,
                   navController: NavController,
                   onNavToLogin:()->Unit){


    val emailVerifiedResult by authViewModel.emailVerified.observeAsState()
    val showPopUp = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isLoading by authViewModel.isUserLoading.collectAsState()

    LaunchedEffect(emailVerifiedResult) {
        if (emailVerifiedResult?.isSuccess == true) {
            showPopUp.value = false
            Toast.makeText(context, "Email Verified", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.RegisterScreen.route) { inclusive = true }
            }
        }
    }


    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Column {
                Text("Enter your Personal Details to Register")
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.surname,
                    maxLines = 1,
                    onValueChange = { viewModel.surname = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_drive_file_rename_outline_24),
                            contentDescription = ""
                        )
                    },
                    placeholder = { Text("Surname") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.firstName,
                    maxLines = 1,
                    onValueChange = { viewModel.firstName = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_drive_file_rename_outline_24),
                            contentDescription = ""
                        )
                    },
                    placeholder = { Text("First Name") }
                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.email,
                    maxLines = 1,
                    onValueChange = { viewModel.email = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = ""
                        )
                    },
                    placeholder = { Text("Email") }
                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.password,
                    maxLines = 1,
                    onValueChange = { viewModel.password = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_no_encryption_24),
                            contentDescription = ""
                        )
                    },
                    placeholder = { Text("Password") },
                    visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.passwordVisible = !viewModel.passwordVisible
                        }) {
                            Icon(
                                painter = if (viewModel.passwordVisible) painterResource(id = R.drawable.baseline_visibility_off_24) else
                                    painterResource(id = R.drawable.baseline_visibility_24),
                                contentDescription = ""
                            )
                        }
                    }

                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.confirmPassword,
                    maxLines = 1,
                    onValueChange = { viewModel.confirmPassword = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_no_encryption_24),
                            contentDescription = ""
                        )
                    },
                    placeholder = { Text("Confirm Password") },
                    visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                )
                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    if(viewModel.surname.isEmpty()||
                        viewModel.firstName.isEmpty()||
                        viewModel.email.isEmpty()||
                        viewModel.password.isEmpty()||
                        viewModel.confirmPassword.isEmpty()){
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
                    }else if(!viewModel.password.equals(viewModel.confirmPassword)){
                        Toast.makeText(context, "passwords do not match", Toast.LENGTH_LONG).show()
                    }else{
                        authViewModel.resetHasNavigated()
                        authViewModel.signUp(viewModel.email.trim(), viewModel.password, viewModel.surname, viewModel.firstName)
                        showPopUp.value = true
                    }

                }, shape = RoundedCornerShape(4.dp), modifier = Modifier.fillMaxWidth()) {
                    Text("Register")
                }
                TextButton(onClick = { onNavToLogin() }) {
                    Text("Already have an account, Login", color = MaterialTheme.colorScheme.onBackground)
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
        if (showPopUp.value) {

            VerifyEmailPopUP(authViewModel = authViewModel,
                viewModel = viewModel,
                onClose = { showPopUp.value = false },
                onVerifyClicked = {authViewModel.checkEmailVerification()}
            )
        }
    }

}

//@SuppressLint("ViewModelConstructorInComposable")
//@Preview(showBackground = true)
//@Composable
//fun RegisterScreenPreview(){
//    RegisterScreen(viewModel = QuizAppViewModel(), authViewModel = AuthViewModel(), navController, {})
//    VerifyEmailPopUP(authViewModel = AuthViewModel(), viewModel = QuizAppViewModel(), {},{})
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyEmailPopUP(
    authViewModel: AuthViewModel,
    viewModel: QuizAppViewModel,
    onClose: ()-> Unit,
    onVerifyClicked: ()-> Unit
){

    BasicAlertDialog(onDismissRequest = {}, properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.padding(32.dp)
        ) {

        Column(modifier = Modifier.fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {

            Icon(imageVector = Icons.Default.Email, contentDescription = "", tint = colorResource(id = R.color.blue_700), modifier = Modifier
                .size(64.dp).background(color = colorResource(id = R.color.blue_200), shape = CircleShape).padding(10.dp))
            Text("Verify your email address", fontWeight = FontWeight.SemiBold, fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary)
            Text("We've sent an email to:", color = MaterialTheme.colorScheme.secondary)
            Text(text = viewModel.email, color = MaterialTheme.colorScheme.primary)

            Column(Modifier
                .background(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.background).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Text("Didn't receive the code? Check your spam folder or request a new code.",
                    textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.primary)

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = {onVerifyClicked()}, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(5.dp), border = BorderStroke(1.dp, color = Color.Gray), content = {
                    Text("Check Verification", color = MaterialTheme.colorScheme.primary)
                })

                OutlinedButton(onClick = {
                    authViewModel.resendVerificationEmail({},{})
                }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, color = Color.Transparent), content = {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                    Text("Resend Email", color = MaterialTheme.colorScheme.primary)
                })
            }

            OutlinedButton(onClick = {onClose()}, shape = RoundedCornerShape(5.dp), border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.errorContainer), content = {
                Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = MaterialTheme.colorScheme.error)
                Text("Cancel", color = MaterialTheme.colorScheme.error)
            })

        }

    }
}