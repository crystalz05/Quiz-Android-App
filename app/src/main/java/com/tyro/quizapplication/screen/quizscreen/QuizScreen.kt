package com.tyro.quizapplication.screen.quizscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tyro.quizapplication.R
import com.tyro.quizapplication.auth.AuthState
import com.tyro.quizapplication.data.entity.Question
import com.tyro.quizapplication.data.misc.formatTime
import com.tyro.quizapplication.data.misc.toTitleCase
import com.tyro.quizapplication.navigation.Screen
import com.tyro.quizapplication.viewmodel.AuthViewModel
import com.tyro.quizapplication.viewmodel.QuestionViewModel
import com.tyro.quizapplication.viewmodel.QuizResultViewModel
import com.tyro.quizapplication.viewmodel.QuizViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    questionViewModel: QuestionViewModel,
    quizViewModel: QuizViewModel,
    quizResultViewModel: QuizResultViewModel
){

    val questions by questionViewModel.questions.collectAsState()
    val isLoadingQuestions by questionViewModel.isLoading.collectAsState()

    val selectedAnswers by questionViewModel.selectedAnswers.collectAsState()

    val timeLeft by quizViewModel.quizTimeLeft.collectAsState()

    if(timeLeft <= 0){
        questionViewModel.calculateScore()
        navController.navigate(Screen.QuizCompletedScreen.route){
            popUpTo(Screen.QuizScreen.route) {inclusive = true}
        }
        if(!authViewModel.isAnonymous){
            quizViewModel.createQuiz(questions.getOrNull(0)?.difficulty?: "", questions.getOrNull(0)?.type ?: "", questions)
        }
    }

    if (isLoadingQuestions) {
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

    val isSaving = quizResultViewModel.isSaving.collectAsState()

    val showPopup = rememberSaveable { mutableStateOf(false) }
    val confirmationPopUp = rememberSaveable { mutableStateOf(true) }

    if (isSaving.value) {
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

    Scaffold {innerPadding ->
        val context = LocalContext.current

        val maxQuestion = questions.size
        val currentQuestion = remember { mutableIntStateOf(0) }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
            .padding(top = 30.dp)) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(questions.getOrNull(0)?.type?.toTitleCase() ?: "", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("${currentQuestion.intValue+1} / ${questions.size}")
                Button(onClick = {
//                    quizViewModel.createQuiz(questions[0].difficulty, questions[0].type, questions)
//                    questionViewModel.calculateScore()
//                    navController.navigate(Screen.HomeScreen.route)
                    showPopup.value = true
                }, shape = RoundedCornerShape(5.dp), content = {
                    Text("End Quiz", color = MaterialTheme.colorScheme.onErrorContainer)
                }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer))
            }

            val progress = if (questions.isNotEmpty()) {
                currentQuestion.intValue / questions.size.toFloat()
            } else {
                0f
            }
            LinearProgressIndicator(
                progress = { progress},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
                    .height(10.dp), color = MaterialTheme.colorScheme.primary
            )
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(color = MaterialTheme.colorScheme.surface,shape = RoundedCornerShape(8.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Time Remaining", color = MaterialTheme.colorScheme.onSurface)
                        Text(text = formatTime(timeLeft), color = colorResource(id = R.color.green_700), fontSize = 45.sp, fontWeight = FontWeight.Bold)
                    }
                    QuestionItem(
                        quizViewModel,
                        authViewModel,
                        quizResultViewModel,
                        questionViewModel,
                        questions,
                        maxQuestion,
                        currentQuestion.intValue,
                        onNext = {
                            if (currentQuestion.intValue < questions.size - 1) {
                                currentQuestion.intValue++
                            }
                        },
                        onPrevious = {
                            if (currentQuestion.intValue > 0) {
                                currentQuestion.intValue--
                            }
                        },
                        navController = navController,
                        selectedAnswers = selectedAnswers
                    )
                }
            }
        }
    }
    if(showPopup.value){
        BasicAlertDialog(
            onDismissRequest = {showPopup.value = false},
            content =
                {
                    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .background(color = colorResource(id = R.color.orange_300), shape = RoundedCornerShape(12.dp)).padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(modifier = Modifier.padding(16.dp), text = "Are you sure you want to end the test, your progress won't be saved!", textAlign = TextAlign.Center, color = Color.Red, fontWeight = FontWeight.SemiBold
                        )
                        Row {
                            Button(onClick = {showPopup.value = false}, Modifier.width(100.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White)) { Text("No", color = Color.Red) }

                            Spacer(modifier = Modifier.width(26.dp))
                            Button(onClick = {
                                quizViewModel.endQuiz()
                                navController.navigate(Screen.HomeScreen.route)
                            }, Modifier.width(100.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) { Text("Yes", color = Color.White) }

                        }
                    }

                }
        )
    }

    if(confirmationPopUp.value){
        BasicAlertDialog(
            onDismissRequest = {showPopup.value = false},
            content =
                {
                    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .background(color = colorResource(id = R.color.green_500), shape = RoundedCornerShape(12.dp)).padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(modifier = Modifier.padding(16.dp), text = "Are you ready to begin the Quiz?", textAlign = TextAlign.Center, color = Color.White)
                        Row {
                            Button(onClick =
                                {
                                    confirmationPopUp.value = false
                                    quizViewModel.endQuiz()
                                    navController.navigate(Screen.HomeScreen.route)
                                }
                                , Modifier.width(100.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) { Text("No", color = Color.White) }
                            Spacer(modifier = Modifier.width(26.dp))
                            Button(onClick = {
                                confirmationPopUp.value = false
                                quizViewModel.startQuiz(onTimeOut = {
                                    questionViewModel.calculateScore()
                                })
                            }, Modifier.width(100.dp), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.white))) { Text("Yes", color = colorResource(id = R.color.green_800)) }
                        }
                    }

                }
        )
    }



}


//@Preview(showBackground = true)
//@Composable
//fun QuizScreenPreview(){
//    QuizScreen()
//}

@Composable
fun QuestionItem(
    quizViewModel: QuizViewModel,
    authViewModel: AuthViewModel,
    quizResultViewModel: QuizResultViewModel,
    questionViewModel: QuestionViewModel,
    questions: List<Question>,
    maxQuestion: Int,
    currentQuestion: Int,
    onPrevious:() -> Unit,
    onNext: ()-> Unit,
    navController: NavController,
    selectedAnswers: Map<Int, String>,
){

    fun onOptionSelected(option: String){
        questionViewModel.selectAnswer(
            questionIndex = currentQuestion,
            answer = option)
    }

    val options = questions.getOrNull(currentQuestion)?.options ?: emptyList()
    val selectedOption = selectedAnswers[currentQuestion] ?: ""

    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(40.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Column(modifier = Modifier.height(100.dp).padding(bottom = 20.dp)) {
                Text(questions.getOrNull(currentQuestion)?.questionText ?: "No question yet",
                    fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }

            options.forEach{ option ->

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    )
                    .border(color = MaterialTheme.colorScheme.onBackground, width = 1.dp, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = null
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(onClick = {
                    if(currentQuestion > 0){
                        onPrevious()
                    }
                },
                    modifier = Modifier
                        .height(46.dp)
                        .weight(1f), shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
                    ) {
                    Text("Previous", style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary)

                }
                if(currentQuestion+1 >= maxQuestion){
                    Button(onClick = {
                        questionViewModel.calculateScore()
                        navController.navigate(Screen.QuizCompletedScreen.route){
                            popUpTo(0) {inclusive = true}
                        }
                        if(!authViewModel.isAnonymous){
                            quizViewModel.createQuiz(questions.getOrNull(0)?.difficulty?: "", questions.getOrNull(0)?.type ?: "", questions)
                        }

                    },modifier = Modifier
                        .height(46.dp)
                        .weight(1f), shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer))
                    {
                        Text("Submit", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }else{
                    Button(onClick = {onNext()},modifier = Modifier
                        .height(46.dp)
                        .weight(1f), shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary))
                    {
                        Text("Next", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }

            }

        }
    }



}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun confirmationPopUp(
//    navController: NavController,
//    dismissPopUp: Boolean
//){
//
//
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ConfirmationPopupPreview(){
//    confirmationPopUp()
//}