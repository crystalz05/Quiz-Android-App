package com.tyro.quizapplication.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.tyro.quizapplication.data.dao.QuestionDao
import com.tyro.quizapplication.data.entity.Question
import kotlinx.coroutines.tasks.await

class QuestionRepository(
    private val dao: QuestionDao,
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun getLocalQuestions(difficulty: String, type: String): List<Question>{
        return dao.getByDifficultyAndType(difficulty, type)
    }

    suspend fun syncFromFireStore(){
        val snapshot = firestore.collection("quiz_questions").get().await()
        val questions = snapshot.map { doc ->

            val options = doc["options"] as List<String>
            Question(
                id = doc.id,
                questionText = doc.getString("question")?:"",
                difficulty = doc.getString("difficulty")?:"",
                type = doc.getString("type")?:"",
                options = options,
                correctAnswerIndex = (doc["correctAnswer"] as Long).toInt()
            )
        }
        dao.clearAll()
        dao.insertAll(questions)
    }
}