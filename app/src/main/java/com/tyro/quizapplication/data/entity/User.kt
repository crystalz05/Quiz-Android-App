package com.tyro.quizapplication.data.entity

data class User(
    val uid: String = "",
    val email: String = "",
    val surname: String = "",
    val firstname: String = "",
    val profilePictureUrl: String?= null,
    val totalScore: Int = 0,
    val quizzesTaken: Int = 0,
    val lastLogin: Long = 0L,
    val quizHistory: List<QuizResult> = emptyList(),
)

