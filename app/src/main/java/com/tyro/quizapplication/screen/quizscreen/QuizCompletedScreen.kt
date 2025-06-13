package com.tyro.quizapplication.screen.quizscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyro.quizapplication.R

@Composable
fun QuizCompletedScreen(){
    //dummy item declaration
    val itemList = listOf("1", "2", "3", "4", "2", "3", "4")

    Scaffold {innerPadding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
            .padding(top = 30.dp)) {

            Column(modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                Text("Quiz Complete!", color = colorResource(id = R.color.green_700), fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Text("Here is your result", color = Color.Gray, fontSize = 18.sp)
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp).padding(bottom = 10.dp)
                .background(color = colorResource(id = R.color.green_500).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("20%", color = colorResource(id = R.color.green_700), fontSize = 55.sp, fontWeight = FontWeight.Bold)
                Text("1 out of 5 correct", color = Color.Gray, fontSize = 18.sp)
            }
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

            Box(modifier = Modifier.fillMaxSize()) {
                val listState = rememberLazyListState()

                LazyColumn(modifier = Modifier.fillMaxWidth().padding(bottom = 100.dp),
                    state = listState
                    ) {

                    items(itemList){ item ->
                        ReviewCard()
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 100.dp))

                Column(modifier = Modifier.align(Alignment.BottomCenter), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Button(onClick = {},modifier = Modifier.fillMaxWidth()
                        .height(46.dp), shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black))
                    {

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "",
                                tint = Color.White, modifier = Modifier.size(20.dp)
                            )
                            Text("Take Quiz Again", style = MaterialTheme.typography.titleMedium)}
                    }
                    OutlinedButton(onClick = {},modifier = Modifier.fillMaxWidth()
                        .height(46.dp), shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black))
                    {

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "",
                                tint = Color.Black, modifier = Modifier.size(20.dp)
                            )
                            Text("Go to Home", style = MaterialTheme.typography.titleMedium)}
                    }
                }

            }

        }
    }

}

//Exam Review Card item
@Composable
fun ReviewCard(){
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, Color.LightGray.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight().background(color = Color.White).padding(vertical = 8.dp)
    ){
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            .background(Color.White),
        ){

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                   Icon(imageVector = Icons.Rounded.Clear, contentDescription = "incorrect",
                       modifier = Modifier.border(width = 1.dp, shape = CircleShape, color = Color.Red).size(20.dp).padding(2.dp),
                       tint = Color.Red
                       )
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text("What is the capital of France?", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                        Row {
                            Text("Your Answer: ", color = Color.Gray, fontSize = 13.sp)
                            Text("Time Expired", color = Color.Red, fontSize = 13.sp)
                        }
                        Text("Correct: Paris", color = colorResource(id = R.color.green_500), fontWeight = FontWeight.Medium)

                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuizCompletedScreenPreview(){
    QuizCompletedScreen()
}