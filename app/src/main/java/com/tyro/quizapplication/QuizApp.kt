package com.tyro.quizapplication

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.tyro.quizapplication.data.misc.QuizGraph

class QuizApp: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseFirestore.setLoggingEnabled(true)
        QuizGraph.provide(this)

    }
}