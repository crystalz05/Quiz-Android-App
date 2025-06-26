package com.tyro.quizapplication.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tyro.quizapplication.R
import com.tyro.quizapplication.screen.homescreensmallcomponents.AccountProfileCard
import com.tyro.quizapplication.screen.homescreensmallcomponents.QuizSubjectItem
import com.tyro.quizapplication.screen.homescreensmallcomponents.ScoreHistoryCard
import com.tyro.quizapplication.viewmodel.AuthViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.tyro.quizapplication.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    profileScreen:()-> Unit,
    historyScreen:()->Unit
){

    val user by authViewModel.currentUserDetails.collectAsState()

    val pagerState = rememberPagerState(pageCount = {4})

    val emailVerifiedResult by authViewModel.emailVerified.observeAsState()
    val hasCheckedVerification = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasCheckedVerification.value) {
            authViewModel.checkEmailVerification()
            hasCheckedVerification.value = true
        }
    }

    LaunchedEffect(emailVerifiedResult) {
        if (emailVerifiedResult?.isSuccess == false) {
            authViewModel.logout()
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.HomeScreen.route) { inclusive = true }
            }
        }
    }

    val isLoadingUser = user.firstname.isEmpty() && !authViewModel.isAnonymous

    if (isLoadingUser) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
        return
    }
    val displayName = if (authViewModel.isAnonymous) "Guest" else user.firstname
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background, titleContentColor = Color.White),
                title ={Text("Welcome, $displayName", color = MaterialTheme.colorScheme.onSecondary, style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp))},
                actions = {
                    IconButton(onClick = {profileScreen()}) {
                        Icon(modifier = Modifier
                            .size(40.dp), tint = MaterialTheme.colorScheme.onBackground,
                            imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            )
        },
        content = { innerPadding ->

        LazyColumn(modifier = Modifier
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()) {
            item() {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    HorizontalPager(
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        pageSpacing = 1.dp,
                        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                            item ->
                        QuizSubjectItem(navController)

                    }

                    ScoreHistoryCard(onNavigateToHistory = {historyScreen()})
                    AccountProfileCard(authViewModel, onNavigateToProfile = {profileScreen()})
                }
            }
        }
        }
    )
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
//    HomeScreen(authViewModel = AuthViewModel(
//        savedStateHandle = { }
//    ), {},{})
}