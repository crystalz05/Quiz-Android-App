package com.tyro.quizapplication.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.tyro.quizapplication.data.entity.QuizResult
import kotlinx.coroutines.tasks.await

class QuizResultRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun saveQuizResult(
        userId: String,
        quizId: String,
        quizType: String,
        score: Int,
        totalQuestions: Int,
        correctAnswers: Int,
    ): Result<Boolean>{

        return try{
            val result = QuizResult(
                userId = userId,
                quizId = quizId,
                quizType = quizType,
                score = score,
                totalQuestions = totalQuestions,
                correctAnswers = correctAnswers,
                completedAt = System.currentTimeMillis()
            )

            firestore.collection("quiz_results").add(result).await()
                    Result.success(true)
        }catch (e: Exception){
            Log.d("Quiz Result Repository", "Saving failed $e")
            Result.failure(e)
        }
    }

    suspend fun getQuizResultsByUserId(userid: String): Result<List<QuizResult>>{
        return try {

            val snapShot = firestore.collection("quiz_results")
                .whereEqualTo("userId", userid).get().await()

            val results = snapShot.toObjects(QuizResult::class.java)
            Result.success(results)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}
