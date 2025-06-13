package com.tyro.quizapplication.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tyro.quizapplication.data.entity.QuizResult
import com.tyro.quizapplication.data.entity.User
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
    ) {
    suspend fun signUp(
        email: String,
        password: String,
        surname: String,
        firstname: String

    ): Result<Boolean>  = try{
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val fireBaseUser = authResult.user?: throw Exception("User registration failed")

        val user = User(
            uid = fireBaseUser.uid,
            email = fireBaseUser.email?: email,
            surname = surname,
            firstname = firstname,
            profilePictureUrl = null,
            totalScore = 0,
            quizzesTaken = 0,
            lastLogin = System.currentTimeMillis(),
            quizHistory = emptyList()
        )

        firestore.collection("users").document(user.uid).set(user).await()

        Result.success(true)
    }catch (e: Exception){
        Result.failure(e)
    }


    suspend fun signIn(
        email: String,
        password: String
    ): Result<Boolean> = try{

        auth.signInWithEmailAndPassword(email, password).await()
        Result.success(true)
    }catch (e: Exception){
        Result.failure(e)
    }

}
