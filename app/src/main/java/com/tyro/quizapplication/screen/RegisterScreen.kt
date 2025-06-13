package com.tyro.quizapplication.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tyro.quizapplication.R
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.QuizAppViewModel


@Composable
fun RegisterScreen(viewModel: QuizAppViewModel,
                   authViewModel: AuthViewModel,
                   onNavToLogin:()->Unit){


    val context = LocalContext.current

    Scaffold() {innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Column {
                Text("Enter your Personal Details to Register")
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.surname,
                    onValueChange = {viewModel.surname = it},
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_drive_file_rename_outline_24), contentDescription = "")},
                    placeholder = { Text("Surname") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.firstName,
                    onValueChange = {viewModel.firstName = it},
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_drive_file_rename_outline_24), contentDescription = "")},
                    placeholder = { Text("First Name") }
                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.email,
                    onValueChange = {viewModel.email = it},
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_person_24), contentDescription = "")},
                    placeholder = { Text("Email") }
                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.password,
                    onValueChange = {viewModel.password = it},
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_no_encryption_24), contentDescription = "")},
                    placeholder = { Text("Password") },
                    visualTransformation = if(viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {viewModel.passwordVisible = !viewModel.passwordVisible}) {
                            Icon(painter = if(viewModel.passwordVisible) painterResource(id = R.drawable.baseline_visibility_off_24) else
                                painterResource(id = R.drawable.baseline_visibility_24)
                                ,
                                contentDescription = ""
                                )
                        }
                    }

                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.confirmPassword,
                    onValueChange = {viewModel.confirmPassword = it},
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_no_encryption_24), contentDescription = "")},
                    placeholder = { Text("Confirm Password") },
                    visualTransformation = if(viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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

                        authViewModel.signUp(viewModel.email, viewModel.password, viewModel.surname, viewModel.firstName)
//                        viewModel.email = ""
//                        viewModel.password = ""
//                        viewModel.surname = ""
//                        viewModel.firstName = ""
                    }

                }, shape = RoundedCornerShape(4.dp), modifier = Modifier.fillMaxWidth()) {
                    Text("Register")
                }
                TextButton(onClick = {onNavToLogin()}) {
                    Text("Already have an account, Login")
                }


            }


        }

    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview(){
    RegisterScreen(viewModel = QuizAppViewModel(), authViewModel = AuthViewModel(), {})
}