package com.tyro.quizapplication.screen

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tyro.quizapplication.R


@Composable
fun LoginScreen(){


    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Scaffold() {innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            ) {

            Column {
                Text("Enter your login credentials")
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username.value,
                    onValueChange = {username.value = it},
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_person_24), contentDescription = "")},
                    placeholder = { Text("Email") }
                    )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password.value,
                    onValueChange = {password.value = it},
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.baseline_no_encryption_24), contentDescription = "")},
                    placeholder = { Text("Password") }
                    )

                Spacer(modifier = Modifier.height(10.dp))
                Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Right, text = "Forgot Password")
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {}, shape = RoundedCornerShape(4.dp), modifier = Modifier.fillMaxWidth()) {
                    Text("Login")
                }
                TextButton(onClick = {}) {
                    Text("Don't have account? Register here")
                }
                Spacer(modifier = Modifier.height(30.dp))
                IconButton(onClick = {}, modifier = Modifier.size(60.dp).align(alignment = Alignment.CenterHorizontally)
                    ,  ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_fingerprint_24), contentDescription = "",
                        tint = colorResource(id = R.color.purple_200)

                        )

                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_700)),
                    shape = RoundedCornerShape(4.dp), modifier = Modifier.fillMaxWidth()) {
                    Text("Continue as Guest")
                }

            }


        }

    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}