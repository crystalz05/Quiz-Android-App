package com.tyro.quizapplication.screen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.tyro.quizapplication.auth.AuthState
import com.tyro.quizapplication.navigation.Screen
import com.tyro.quizapplication.viewmodel.QuestionViewModel
import com.tyro.quizapplication.viewmodel.QuizResultViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    profileScreen:()-> Unit,
    historyScreen:()->Unit,
    questionsViewModel: QuestionViewModel,
    quizResultViewModel: QuizResultViewModel
){

    val user by authViewModel.currentUserDetails.collectAsState()
    val pagerState = rememberPagerState(pageCount = {4})

    val questionTypes = listOf("mathematics", "computer", "english", "graphics")

    val emailVerifiedResult by authViewModel.emailVerified.observeAsState()
    val hasCheckedVerification = remember { mutableStateOf(false) }
    val historyData by quizResultViewModel.quizResult.collectAsState()
    val questions by questionsViewModel.questions.collectAsState()

    val authState by authViewModel.authState.collectAsState()
    val hasNavigated by authViewModel.hasNavigated.collectAsState()

    LaunchedEffect(authState) {
            if (authState == AuthState.Unverified) {
                authViewModel.logout()
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.HomeScreen.route) { inclusive = true }
                }

        }
    }

    LaunchedEffect(user.uid) {
        if (user.uid.isNotEmpty() && !authViewModel.isAnonymous) {
            quizResultViewModel.getQuizResultsById(user.uid)
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.fetchCurrentUser()
        questionsViewModel.syncQuestions()
    }

    val isLoadingUser by authViewModel.isUserLoading.collectAsState()

    if (isLoadingUser) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            authViewModel.fetchCurrentUser()
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
                        QuizSubjectItem(
                            questionsViewModel,
                            navController,
                            questionTypes[pagerState.currentPage]
                        )
                    }

                    ScoreHistoryCard(historyData.sortedBy { it.completedAt }.takeLast(2), onNavigateToHistory = {historyScreen()})
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