package com.tyro.quizapplication.data.entity

data class Quiz(
    val id: String = "",
    val difficulty: String = "",
    val type: String = "",
    val questionIds: List<String> = listOf()
)
