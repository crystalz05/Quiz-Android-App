package com.tyro.quizapplication.screen.quizscreen

import android.widget.ProgressBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyro.quizapplication.R


@Composable
fun QuizScreen(){

    Scaffold {innerPadding ->

        val context = LocalContext.current

        val questions = (1..5).toList()
        val currentQuestion = remember { mutableIntStateOf(1) }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
            .padding(top = 30.dp)) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Mathematics Quiz", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("${currentQuestion.intValue} / ${questions.size}")
            }

            val progress = currentQuestion.intValue/questions.size.toFloat()

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
                    .height(10.dp), color = Color.Black
            )
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(color = Color.LightGray.copy(alpha = 0.2f),shape = RoundedCornerShape(8.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Time Remaining", color = Color.Gray)
                        Text("16s", color = colorResource(id = R.color.green_700), fontSize = 45.sp, fontWeight = FontWeight.Bold)
                    }
                    QuestionItem(
                        onNext = { currentQuestion.intValue++},
                        onPrevious = { currentQuestion.intValue-- }
                    )
                }
            }
        }
    }



}


@Preview(showBackground = true)
@Composable
fun QuizScreenPreview(){
    QuizScreen()
}

@Composable
fun QuestionItem(onPrevious:() -> Unit, onNext: ()-> Unit){

    val options = listOf("food", "rice", "water", "air")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(40.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text("What is the Capital of France?", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

            options.forEach{ option ->

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    )
                    .border(color = Color.LightGray, width = 1.dp, shape = RoundedCornerShape(8.dp))
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
                OutlinedButton(onClick = {onPrevious()},
                    modifier = Modifier
                        .height(46.dp)
                        .weight(1f), shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, color = Color.Black)
                    ) {
                    Text("Previous", style = MaterialTheme.typography.titleMedium, color = Color.Black)

                }
                Button(onClick = {onNext()},modifier = Modifier
                    .height(46.dp)
                    .weight(1f), shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black))
                {
                    Text("Next", style = MaterialTheme.typography.titleMedium)
                }
            }

        }
    }



}