package com.tyro.quizapplication.screen

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tyro.quizapplication.R
import com.tyro.quizapplication.data.entity.QuizResult
import com.tyro.quizapplication.screen.profilescreencmallcontainers.AccountSettingCard
import com.tyro.quizapplication.screen.profilescreencmallcontainers.SettingsCard
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.QuizResultViewModel
import com.tyro.quizapplication.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    themeViewModel: ThemeViewModel,
    navController: NavController,
    authViewModel: AuthViewModel,
    quizResultViewModel: QuizResultViewModel
){

    val user by authViewModel.currentUserDetails.collectAsState()
    val isLoading by authViewModel.isUserLoading.collectAsState()

    val averageScore = if (user.quizzesTaken > 0) user.totalScore / user.quizzesTaken else 0

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background),
        topBar = {

            TopAppBar(title = {
                Text("User Profile") },
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                })

        }

    ) { innerPadding ->
        LazyColumn(modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .fillMaxSize()) {

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                    ) {
                        Column(modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(top = 160.dp)
                            .padding(16.dp)
                            .fillMaxSize()
                            .align(Alignment.TopCenter),
                            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("${user.surname} ${user.firstname}", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurface)
                            Text(user.email, color = MaterialTheme.colorScheme.onSurface)

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                Card(modifier = Modifier
                                    .width(150.dp)
                                    .wrapContentHeight()) {
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = MaterialTheme.colorScheme.secondary,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Icon(imageVector = Icons.Default.CheckCircle,
                                            tint = colorResource(id = R.color.blue_800),
                                            contentDescription = "Quizes")
                                        Text("Quizzes", fontWeight = FontWeight.Medium)
                                        Text("${user.quizzesTaken}", fontWeight = FontWeight.Medium)
                                    }
                                }
                                Card(modifier = Modifier
                                    .width(150.dp)
                                    .wrapContentHeight()) {
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = MaterialTheme.colorScheme.secondary,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(painter = painterResource(id = R.drawable.baseline_stars_24),
                                            tint = colorResource(id = R.color.orange) ,
                                            contentDescription = "Quizes")
                                        Text("Average", fontWeight = FontWeight.Medium)
                                        Text("$averageScore", fontWeight = FontWeight.Medium)

                                    }
                                }
                            }

                            HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(4.dp))

                            OutlinedButton(onClick = {}, border = BorderStroke(width = 1.dp, color = Color.LightGray),
                                shape = RoundedCornerShape(8.dp),
                                content = {
                                Icon(imageVector = Icons.Default.Share, tint = MaterialTheme.colorScheme.onSurface, contentDescription = "share")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Share Profile", color = MaterialTheme.colorScheme.onSurface)
                            })



                        }
                        Column(modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.surface,
                                        MaterialTheme.colorScheme.secondaryContainer
                                    ),
                                )
                            )
                            .fillMaxWidth()
                            .height(100.dp)
                            .align(Alignment.TopCenter)
                            .padding(10.dp), horizontalAlignment = Alignment.End) {

                            Button(onClick = {}, shape = RoundedCornerShape(8.dp),
                                modifier = Modifier,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit",
                                        modifier = Modifier.size(16.dp))
                                    Text("Edit Profile Photo", color = MaterialTheme.colorScheme.onPrimary)
                                }

                            }

                        }
                        Card(modifier = Modifier
                            .padding(top = 50.dp)
                            .align(Alignment.TopCenter)
                            .height(100.dp)
                            .width(100.dp),
                            shape = CircleShape, elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.White), contentAlignment = Alignment.Center){

                                Box(modifier = Modifier
                                    .fillMaxSize(0.9f)
                                    .background(color = Color.LightGray, shape = CircleShape)){

                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Settings", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)

                Spacer(modifier = Modifier.height(8.dp))

                SettingsCard(themeViewModel)

                Spacer(modifier = Modifier.height(8.dp))

                AccountSettingCard(authViewModel, quizResultViewModel, navController)

                Spacer(modifier = Modifier.height(8.dp))

                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Quiz App v.1.0.0", color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("© 2025 Quiz App", color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))

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
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
//    ProfileScreen()
}