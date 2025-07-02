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
import androidx.compose.material.icons.rounded.Check
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import com.tyro.quizapplication.R
import com.tyro.quizapplication.data.entity.Question
import com.tyro.quizapplication.navigation.Screen
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.QuestionViewModel
import com.tyro.quizapplication.viewmodel.QuizResultViewModel
import com.tyro.quizapplication.viewmodel.QuizViewModel

@Composable
fun QuizCompletedScreen(
    questionsViewModel: QuestionViewModel,
    quizViewModel: QuizViewModel,
    authViewModel: AuthViewModel,
    quizResultViewModel: QuizResultViewModel,
    navController: NavController){

    val questions by questionsViewModel.questions.collectAsState()
    val safeQuestions = questions ?: emptyList()
    val score = questionsViewModel.score.collectAsState()
    val correctAnswers = questionsViewModel.correctAnswers.collectAsState()


    val quizData = quizViewModel.currentQuiz.collectAsState().value
    val userId = authViewModel.currentUserDetails.collectAsState().value.uid
    val totalQuestions = safeQuestions.size

    LaunchedEffect(Unit) {

        authViewModel.updateScoreAndQuiz(score.value.toLong())
        quizResultViewModel.saveQuizResult(userId, quizData.id, quizData.type, score.value, totalQuestions,correctAnswers.value)
    }

    Scaffold {innerPadding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
            .padding(top = 30.dp)) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                Text("Quiz Complete!", color = colorResource(id = R.color.green_700), fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Text("Here is your result", color = Color.Gray, fontSize = 18.sp)
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 10.dp)
                .background(
                    color = colorResource(id = R.color.green_500).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("${score.value}%", color = colorResource(id = R.color.green_700), fontSize = 55.sp, fontWeight = FontWeight.Bold)
                Text("${correctAnswers.value} out of ${questions.size} correct", color = Color.Gray, fontSize = 18.sp)
            }
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

            Box(modifier = Modifier.fillMaxSize()) {
                val listState = rememberLazyListState()

                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 105.dp),
                    state = listState
                    ) {

                    items(safeQuestions){ item ->
                        ReviewCard(item, questionsViewModel, questions)
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 105.dp))

                Column(modifier = Modifier.align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Button(onClick = {},modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp), shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary))
                    {

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSecondary, modifier = Modifier.size(20.dp)
                            )
                            Text("Take Quiz Again", style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSecondary
                                )}
                    }
                    OutlinedButton(onClick = {
                        navController.navigate(Screen.HomeScreen.route){
                            popUpTo(0) { inclusive = true }
                        }
                    },modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp), shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary))
                    {

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(20.dp)
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
fun ReviewCard(
    question: Question,
    questionsViewModel: QuestionViewModel,
    questions: List<Question>
){

    val selectedAnswer = questionsViewModel.selectedAnswers.collectAsState()

    val questionIndex = questions.indexOf(question)
    val userAnswer = selectedAnswer.value[questionIndex]
    val correctAnswer = question.options[question.correctAnswerIndex]
    val isCorrect = userAnswer == correctAnswer

    Card(
        shape = RoundedCornerShape(8.dp),
//        border = BorderStroke(width = 1.dp, Color.LightGray.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 8.dp)
    ){
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        ){

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                   Icon(imageVector = if(isCorrect) Icons.Rounded.Check else Icons.Rounded.Clear,
                       contentDescription = if(isCorrect) "Correct" else "Incorrect",
                       modifier = Modifier
                           .border(width = 1.dp, shape = CircleShape,
                               color = if(isCorrect) Color.Green else Color.Red)
                           .size(20.dp)
                           .padding(2.dp),
                       tint = if(isCorrect) Color.Green else Color.Red
                       )
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(question.questionText, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                        Row {
                            Text("Your Answer: ", color = Color.Gray, fontSize = 13.sp)
                            Text(text = "$userAnswer",
                                color = if(isCorrect) Color.Green else Color.Red, fontSize = 13.sp)
                        }
                        Text(question.options[question.correctAnswerIndex], color = colorResource(id = R.color.green_500), fontWeight = FontWeight.Medium)

                    }
                }
            }
        }
    }
}