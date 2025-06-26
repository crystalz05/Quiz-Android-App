package com.tyro.quizapplication

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore

class QuizApp: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseFirestore.setLoggingEnabled(true)
    }
}