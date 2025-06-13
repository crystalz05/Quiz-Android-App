package com.tyro.quizapplication.data.entity

data class QuizResult(
    val userId: String = "",
    val quizId: String = "",
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val correctAnswers: Int = 0,
    val completedAt: Long = 0L
)