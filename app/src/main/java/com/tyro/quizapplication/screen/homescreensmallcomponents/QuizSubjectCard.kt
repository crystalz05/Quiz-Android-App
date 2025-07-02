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
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tyro.quizapplication.R
import com.tyro.quizapplication.navigation.Screen
import com.tyro.quizapplication.viewmodel.QuestionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizSubjectItem(
    questionViewModel: QuestionViewModel,
    navController: NavController,
    questionType: String
){
    val coroutineScope = rememberCoroutineScope()
    val difficulty = listOf("Easy", "Medium", "Hard")
    val selectedDifficulty = remember { mutableStateOf("Easy") }
//    val questions by questionViewModel.questions.collectAsState()

    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(300.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().weight(1f)
                    .background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(16.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(questionType.uppercase(), fontWeight = FontWeight.Medium, fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSecondary)
                    Text("15 Questions Quiz", color = MaterialTheme.colorScheme.onSecondary)

                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    difficulty.forEach{ level ->
                        if(selectedDifficulty.value == level){
                            Button(modifier = Modifier.wrapContentWidth().fillMaxWidth().weight(1f), onClick = {
                            }, shape = RoundedCornerShape(5.dp) ) {
                                Text(level, color = MaterialTheme.colorScheme.onPrimary, maxLines = 1)
                            }
                        }else{
                            OutlinedButton(modifier = Modifier.fillMaxWidth().weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                onClick = {
                                selectedDifficulty.value = level
                            }, shape = RoundedCornerShape(5.dp) ) {
                                Text(level, maxLines = 1, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                        }
                    }
                }
                Button(modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    onClick = {
                        coroutineScope.launch {
                            questionViewModel.loadQuestions(
                                difficulty = selectedDifficulty.value.lowercase(),
                                type = questionType.lowercase())
                        }
                        navController.navigate(Screen.QuizScreen.route)
                    }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription = "", tint = MaterialTheme.colorScheme.onPrimary)

                    Text("Start Quiz", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}
