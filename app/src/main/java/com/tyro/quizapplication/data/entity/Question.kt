package com.tyro.quizapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey val id: String = "",
    val questionText: String = "",
    val difficulty: String = "",
    val type: String = "",
    val options: List<String> = listOf(),
    val correctAnswerIndex: Int = 0,
)
