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

        fireBaseUser.sendEmailVerification()
        firestore.collection("users").document(user.uid).set(user).await()

        Result.success(true)
    }catch (e: Exception){
        Result.failure(e)
    }


    suspend fun signIn(
        email: String,
        password: String
    ): Result<Boolean> = try{

        val result = auth.signInWithEmailAndPassword(email, password).await()
        val fireBaseuser = result.user

        if(fireBaseuser != null && fireBaseuser.isEmailVerified){
            Result.success(true)
        }else{
            Result.failure(Exception("Email is not verified. Please check your inbox"))
        }
    }catch (e: Exception){
        Result.failure(e)
    }

    suspend fun checkEmailVerified(): Result<Boolean> {
        return try {
            val user = auth.currentUser?: return Result.failure(Exception("No Logged in user"))
            user.reload().await()

            if(user.isEmailVerified){
                Result.success(true)
            }else{
                Result.failure(Exception("Email not yet verified"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}
