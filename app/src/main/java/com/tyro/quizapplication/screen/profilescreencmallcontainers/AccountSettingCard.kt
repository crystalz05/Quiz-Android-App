package com.tyro.quizapplication.screen.profilescreencmallcontainers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.twotone.ExitToApp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.twotone.ExitToApp
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tyro.quizapplication.R
import com.tyro.quizapplication.navigation.Screen
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.QuizResultViewModel
import com.tyro.quizapplication.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettingCard(
    authViewModel: AuthViewModel,
    quizResultViewModel: QuizResultViewModel,
    navController: NavController
){

    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background( MaterialTheme.colorScheme.surface).padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                Spacer(modifier = Modifier.height(10.dp))

                if(!authViewModel.isAnonymous){
                    OutlinedButton(onClick = {
                        navController.navigate(Screen.ResetPasswordScreen.route)
                    },
                        shape = RoundedCornerShape(8.dp), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.fillMaxWidth()){
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Outlined.Settings, contentDescription = "advanced settings")
                            Spacer(Modifier.width(8.dp))
                            Text("Change Password", fontWeight = FontWeight.Medium, fontSize = 18.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    showLogoutDialog = true
                },
                    shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth()){
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.ExitToApp, tint = MaterialTheme.colorScheme.onErrorContainer, contentDescription = "advanced settings")
                        Spacer(Modifier.width(8.dp))
                        Text("Sign out", fontWeight = FontWeight.Medium, fontSize = 18.sp, color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }

                if(!authViewModel.isAnonymous){
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = {},
                        shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier.fillMaxWidth()){
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Outlined.Delete, tint = MaterialTheme.colorScheme.onErrorContainer, contentDescription = "advanced settings")
                            Spacer(Modifier.width(8.dp))
                            Text("Delete Account", fontWeight = FontWeight.Medium, fontSize = 18.sp, color = MaterialTheme.colorScheme.onErrorContainer)
                        }
                    }

                }

            }

        }
    }
    if(showLogoutDialog){
        BasicAlertDialog(
            onDismissRequest = {showLogoutDialog = false},
            content =
                {
                    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .background(color = colorResource(id = R.color.orange_300), shape = RoundedCornerShape(12.dp)).padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(modifier = Modifier.padding(16.dp), text = "You are about to log out", textAlign = TextAlign.Center, color = Color.Red, fontWeight = FontWeight.SemiBold
                        )
                        Row {
                            Button(onClick = {showLogoutDialog = false}, Modifier.width(100.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White))
                            { Text("Cancel", color = Color.Red) }

                            Spacer(modifier = Modifier.width(26.dp))
                            Button(onClick = {
                                authViewModel.logout()
                                quizResultViewModel.clearResults()
                                navController.navigate(Screen.LoginScreen.route)
                            }, Modifier.width(100.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme
                                    .colorScheme.error)) { Text("Continue", color = Color.White) }

                        }
                    }

                }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun AccountSettingCardPreview(){
//    AccountSettingCard()
}