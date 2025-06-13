package com.tyro.quizapplication.screen.homescreensmallcomponents

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
fun ScoreHistoryCard() {

    val historyItem = (1..2).toList()

    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, Color.LightGray.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp).padding(horizontal = 16.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            .background(Color.White.copy(alpha = 0.8f)),
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_manage_history_24),
                        contentDescription = "", tint = Color.White,
                        modifier = Modifier.background(color = colorResource(id = R.color.orange),
                                shape = RoundedCornerShape(8.dp)).padding(4.dp)
                        )
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Recent History", fontWeight = FontWeight.Bold)
                        Text("Your latest quiz attempts", fontSize = 10.sp, color = Color.Gray)
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    historyItem.forEach{ history ->
                            Column(modifier = Modifier.background(color = Color.LightGray.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)).fillMaxWidth().padding(8.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                    ) {
                                    Column {
                                        Text("Mathematics", fontWeight = FontWeight.SemiBold)
                                        Text("2h ago", fontSize = 12.sp, color = Color.Gray)
                                    }
                                    Text("85%", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                }
                            }
                    }
                }
                Column(modifier = Modifier.weight(1f).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Tap to view All History", color = Color.Gray)
                }
            }

        }

    }

}


@Preview(showBackground = true)
@Composable
fun ScoreHistroyCardPreview(){
    ScoreHistoryCard()
}