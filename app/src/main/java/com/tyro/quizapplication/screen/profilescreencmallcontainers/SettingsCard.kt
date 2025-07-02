package com.tyro.quizapplication.screen.profilescreencmallcontainers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyro.quizapplication.R
import com.tyro.quizapplication.data.misc.ThemeMode
import com.tyro.quizapplication.viewmodel.ThemeViewModel

@Composable
fun SettingsCard(themeViewModel: ThemeViewModel){


    val themeMode by themeViewModel.themeMode.collectAsState()
    val biometricEnabled = remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                Text("Preferences", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Outlined.Notifications,
                            tint =  MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(30.dp),
                            contentDescription = "Notification")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Notification", fontSize = 16.sp, fontWeight = FontWeight.Medium, color =  MaterialTheme.colorScheme.onSurface)
                    }
                    var isChecked by remember { mutableStateOf(false) }
                    Switch(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.Gray,
                            checkedTrackColor = colorResource(id = R.color.green_700),
                            uncheckedTrackColor = Color.White,
                            checkedBorderColor = colorResource(id = R.color.green_700),
                            uncheckedBorderColor = Color.Gray
                        )
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = R.drawable.baseline_dark_mode_24),
                            tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(30.dp),
                            contentDescription = "Notification")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Dark Mode", fontSize = 16.sp, fontWeight = FontWeight.Medium, color =  MaterialTheme.colorScheme.onSurface)
                    }

                    Switch(
                        checked = themeMode == ThemeMode.DARK,
                        onCheckedChange =
                            {
                                themeViewModel.setThemeMode(if (it) ThemeMode.DARK else ThemeMode.LIGHT)
                            },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.Gray,
                            checkedTrackColor = colorResource(id = R.color.green_700),
                            uncheckedTrackColor = Color.White,
                            checkedBorderColor = colorResource(id = R.color.green_700),
                            uncheckedBorderColor = Color.Gray
                        )
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = R.drawable.baseline_color_lens_24),
                            tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(30.dp),
                            contentDescription = "Notification")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Use system default theme", fontSize = 16.sp, fontWeight = FontWeight.Medium, color =  MaterialTheme.colorScheme.onSurface)
                    }

                    Switch(
                        checked = themeMode == ThemeMode.SYSTEM,
                        onCheckedChange = { isChecked -> if(isChecked)
                        {
                            themeViewModel.useSystemTheme()
                        }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.Gray,
                            checkedTrackColor = colorResource(id = R.color.green_700),
                            uncheckedTrackColor = Color.White,
                            checkedBorderColor = colorResource(id = R.color.green_700),
                            uncheckedBorderColor = Color.Gray
                        )
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = R.drawable.baseline_fingerprint_24),
                            tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(30.dp),
                            contentDescription = "Notification")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Enable Biometric", fontSize = 16.sp, fontWeight = FontWeight.Medium, color =  MaterialTheme.colorScheme.onSurface)
                    }

                    Switch(
                        checked = biometricEnabled.value,
                        onCheckedChange = { biometricEnabled.value = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.Gray,
                            checkedTrackColor = colorResource(id = R.color.green_700),
                            uncheckedTrackColor = Color.White,
                            checkedBorderColor = colorResource(id = R.color.green_700),
                            uncheckedBorderColor = Color.Gray
                        )
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SettingsCardPreview(){
//    SettingsCard()
//}