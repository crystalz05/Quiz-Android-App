package com.tyro.quizapplication.screen

import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.tyro.quizapplication.R

@Composable
fun SplashScreen(){
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(3000)


    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondary), contentAlignment = Alignment.Center)
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(100.dp))
        }

        Spacer(modifier = Modifier.height(25.dp))
        CircularProgressIndicator(color = Color.White)
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    SplashScreen()
}
