package com.tyro.quizapplication.data.misc

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.tyro.quizapplication.data.repository.QuestionRepository
import com.tyro.quizapplication.data.repository.QuizRepository
import com.tyro.quizapplication.data.repository.QuizResultRepository
import com.tyro.quizapplication.data.repository.UserRepository
import com.tyro.quizapplication.injection.Injection

object QuizGraph {

    lateinit var database: QuizAppDatabase

    val questionRepository: QuestionRepository by lazy {
        QuestionRepository(database.questionDao())
    }

    val quizRepository: QuizRepository by lazy{
        QuizRepository()
    }

    val quizResultRepository: QuizResultRepository by lazy{
        QuizResultRepository()
    }

    val userRepository: UserRepository by lazy {
        UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(
            context.applicationContext,
            QuizAppDatabase::class.java,
            "quiz-db"
        ).build()
    }
}