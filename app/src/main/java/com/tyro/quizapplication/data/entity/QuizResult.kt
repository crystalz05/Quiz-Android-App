package com.tyro.quizapplication.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//@Parcelize
data class QuizResult(
    val userId: String = "",
    val quizId: String = "",
    val quizType: String ="",
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val correctAnswers: Int = 0,
    val completedAt: Long = 0L
)
//    : Parcelable