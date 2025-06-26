package com.tyro.quizapplication.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyro.quizapplication.R
import kotlin.math.exp


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navigateBack:()->Unit
){

    //dummy history
    val historyItem = (1..15).toList()
    var sortExpanded by remember { mutableStateOf(false) }
    val sortOptions = listOf("Date", "Score")
    var selectedSortOption by remember { mutableStateOf(sortOptions[0]) }

    var filterExpanded by remember { mutableStateOf(false) }
    val filterOptions = listOf("Subject")
    var selectedFilterOption by remember { mutableStateOf(filterOptions[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz History") },
                navigationIcon = {
                    IconButton(onClick = {navigateBack()}) {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxWidth()) {
            Text("Quiz History", fontWeight = FontWeight.Bold, fontSize = 18.sp)


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {

                    TextButton(onClick = {sortExpanded = true}) {
                        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                            Text("Sort by: ")
                            Text(selectedSortOption)
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Sort Options")
                        }
                    }

                    DropdownMenu( modifier = Modifier.width(100.dp),
                        expanded = sortExpanded,
                        onDismissRequest = { sortExpanded = false }
                    ) {
                        sortOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedSortOption = option
                                    sortExpanded = false
                                }
                            )
                        }
                    }
                }

                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {

                    TextButton(onClick = {filterExpanded = true}) {
                        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                            Text("Sort by: ")
                            Text(selectedFilterOption)
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Sort Options")
                        }
                    }

                    DropdownMenu( modifier = Modifier.width(100.dp),
                        expanded = filterExpanded,
                        onDismissRequest = { filterExpanded = false }
                    ) {
                        filterOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedFilterOption = option
                                    filterExpanded = false
                                }
                            )
                        }
                    }
                }

            }
            HorizontalDivider(Modifier.padding(top = 18.dp), thickness = 1.dp, color = Color.LightGray)

            LazyColumn(modifier = Modifier.padding(horizontal = 4.dp),
                ) {

                items(historyItem){ item ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight().padding(vertical = 8.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.outline)
                        ) {
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
                                .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text("Mathematics", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text("2h ago ", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSecondary)
                                        Text("15/20 correct", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background,
                                    shape = RoundedCornerShape(10.dp)).padding(vertical = 4.dp, horizontal = 8.dp)
                                    ){
                                    Text("85%", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }

            }
            HorizontalDivider(Modifier.padding(bottom = 18.dp), thickness = 1.dp, color = Color.LightGray)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview(){
    HistoryScreen({})
}