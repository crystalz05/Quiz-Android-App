package com.tyro.quizapplication.data.entity

data class Question(
    val id: String = "",
    val questionText: String = "",
    val difficulty: String = "",
    val options: List<String> = listOf(),
    val correctAnswerIndex: Int = 0,
)
