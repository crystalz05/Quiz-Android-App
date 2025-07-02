package com.tyro.quizapplication.screen.homescreensmallcomponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyro.quizapplication.R
import com.tyro.quizapplication.data.entity.QuizResult
import com.tyro.quizapplication.data.misc.formatTimeStamp
import com.tyro.quizapplication.data.misc.toTitleCase
import com.tyro.quizapplication.viewmodel.QuizResultViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScoreHistoryCard(
    recentHistory: List<QuizResult>,
    onNavigateToHistory : ()-> Unit
) {

    Card(
        onClick = {onNavigateToHistory()},
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight().padding(horizontal = 16.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_manage_history_24),
                        contentDescription = "", tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.background(color = MaterialTheme.colorScheme.onBackground,
                                shape = RoundedCornerShape(8.dp)).padding(4.dp)
                        )
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Recent History", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Text("Your latest quiz attempts", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground)
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    recentHistory.sortedByDescending { it.completedAt }.forEach{ history ->
                            Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(8.dp)).fillMaxWidth().padding(8.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                    ) {
                                    Column {
                                        Text(history.quizType.toTitleCase(), fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSecondary)
                                        Text(formatTimeStamp(history.completedAt), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSecondary)
                                    }
                                    Text("${history.score}%", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSecondary)
                                }
                            }
                    }
                }
            }

        }

    }

}


//@Preview(showBackground = true)
//@Composable
//fun ScoreHistroyCardPreview(){
//    ScoreHistoryCard({})
//}