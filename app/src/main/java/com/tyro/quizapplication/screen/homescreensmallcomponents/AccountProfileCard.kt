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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
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
fun AccountProfileCard(){
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
                    Icon(imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "", tint = Color.White,
                        modifier = Modifier.background(color = colorResource(id = R.color.blue_700),
                            shape = RoundedCornerShape(8.dp)).padding(4.dp)
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Your Profile", fontWeight = FontWeight.Bold)
                        Text("Account and Achievements", fontSize = 10.sp, color = Color.Gray)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.background(color = colorResource(id = R.color.blue_100).copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)).fillMaxWidth().padding(8.dp).weight(1f),

                ) {
                    Icon(imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier.background(color = colorResource(id = R.color.white),
                            shape = RoundedCornerShape(8.dp)).padding(4.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("PauL Michael", fontWeight = FontWeight.SemiBold)
                        Text("mikebingp@gmail.com", fontSize = 10.sp, color = Color.Gray)
                    }
                }
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Tap to view profile", color = Color.Gray)
                }
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun AccountProfileCardPreview(){
    AccountProfileCard()
}