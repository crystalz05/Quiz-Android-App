package com.tyro.quizapplication.screen.homescreensmallcomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyro.quizapplication.R

@Composable
fun QuizSubjectItem(){

    val difficulty = listOf("Easy", "Medium", "Hard")
    val selectedDifficulty = remember { mutableStateOf("Easy") }


    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, Color.LightGray.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(300.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            .background(Color.White.copy(alpha = 0.8f)),
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().weight(1f)
                    .background(color = colorResource(id = R.color.blue_100).copy(alpha = 0.5f), shape = RoundedCornerShape(16.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Mathematics", fontWeight = FontWeight.Medium, fontSize = 24.sp)
                    Text("20 Questions quiz")

                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    difficulty.forEach{ level ->
                        if(selectedDifficulty.value == level){
                            Button(modifier = Modifier.wrapContentWidth().fillMaxWidth().weight(1f), onClick = {
                            }, shape = RoundedCornerShape(5.dp) ) {
                                Text(level, color = Color.White, maxLines = 1)
                            }
                        }else{
                            OutlinedButton(modifier = Modifier.fillMaxWidth().weight(1f), onClick = {
                                selectedDifficulty.value = level
                            }, shape = RoundedCornerShape(5.dp) ) {
                                Text(level, maxLines = 1)
                            }
                        }
                    }
                }
                Button(modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription = "")

                    Text("Start Quiz")
                }
            }
        }
    }
}
