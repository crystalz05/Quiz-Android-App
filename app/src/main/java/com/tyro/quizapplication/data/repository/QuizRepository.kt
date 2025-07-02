package com.tyro.quizapplication.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.tyro.quizapplication.data.entity.Question
import com.tyro.quizapplication.data.entity.Quiz
import com.tyro.quizapplication.data.entity.QuizResult

class QuizRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
) {

    suspend fun createQuiz(difficulty: String, type: String, questions: List<Question>): Quiz  {
        val quizRef = firestore.collection("quizzes").document()

        val questionsId = questions.map { it.id }
        val quiz = Quiz(
            id = quizRef.id,
            difficulty = difficulty,
            type = type,
            questionIds = questionsId
        )
        quizRef.set(quiz)
        return quiz
    }
}
