package com.tyro.quizapplication.screen

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tyro.quizapplication.R
import com.tyro.quizapplication.screen.homescreensmallcomponents.AccountProfileCard
import com.tyro.quizapplication.screen.homescreensmallcomponents.QuizSubjectItem
import com.tyro.quizapplication.screen.homescreensmallcomponents.ScoreHistoryCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){

    val pagerState = rememberPagerState(pageCount = {4})

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = Color.White),
                title ={Text("Hello, Michael", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))},
                navigationIcon = {
                    Icon(modifier = Modifier.size(30.dp),
                        imageVector = Icons.Filled.Menu, contentDescription = "",
                        tint = Color.White,
                        )
                },
                actions = {
                    Icon(modifier = Modifier.padding(16.dp).size(40.dp), tint = Color.White,
                        imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                }
                )
        },
        content = { innerPadding ->

        LazyColumn(modifier = Modifier
            .padding(innerPadding).background(colorResource(id = R.color.blue_100).copy(alpha = 0.5f))
            .fillMaxSize()) {
            item() {
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    HorizontalPager(
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        pageSpacing = 1.dp,
                        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                            item ->
                        QuizSubjectItem()

                    }

                    ScoreHistoryCard()
                    AccountProfileCard()
                }
            }
        }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}