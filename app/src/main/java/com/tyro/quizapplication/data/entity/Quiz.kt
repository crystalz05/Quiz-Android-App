package com.tyro.quizapplication.data.entity

data class Quiz(
    val id: String = "",
    val category: String = "",
    val questionIds: List<String> = listOf()
)
