package com.tyro.quizapplication.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tyro.quizapplication.data.entity.QuizResult
import com.tyro.quizapplication.data.entity.User
import kotlinx.coroutines.tasks.await
import android.util.Log
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.delay


class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
    ) {

    suspend fun signUp(
        email: String,
        password: String,
        surname: String,
        firstname: String

    ): Result<Boolean> {
        return try{
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

            Log.d("UserRepository", "Attempting to save user to Firestore")
            try{
                firestore.collection("users").document(user.uid).set(user).await()
            }catch (e: Exception) {
                Log.w("UserRepository", "Failed to save user to Firestore", e)
            }
            resendVerification()
            Result.success(true)

        } catch (e: FirebaseAuthUserCollisionException) {
            // Email already exists, try logging in instead
            return try {
                val signInResult = auth.signInWithEmailAndPassword(email, password).await()
                val existingUser = signInResult.user

                if (existingUser != null && !existingUser.isEmailVerified) {
                    existingUser.sendEmailVerification().await()
                    Result.failure(Exception("Account already exists but is not verified. We've resent the verification email."))
                } else {
                    Result.failure(Exception("Email is already in use and verified. Please log in instead."))
                }
            } catch (loginException: Exception) {
                Result.failure(Exception("Email already in use with a different password. Please log in."))
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Sign-up failed", e)
            Result.failure(e)
        }
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

            if(user.isAnonymous){
                return Result.success(true)
            }

            if(user.isEmailVerified){
                Result.success(true)
            }else{
                Result.failure(Exception("Email not yet verified"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getCurrentUser(): Result<User> {
        val uid = auth.currentUser?.uid ?: return Result.failure(Exception("User not authenticated"))

        repeat(3) { attempt ->
            try {
                val snapshot = firestore.collection("users").document(uid).get().await()
                if (!snapshot.exists()) {
                    return Result.failure(Exception("User document does not exist in Firestore"))
                }

                val user = snapshot.toObject(User::class.java)
                return user?.let { Result.success(it) }
                    ?: Result.failure(Exception("User data not found"))

            } catch (e: FirebaseFirestoreException) {
                if (e.code.name == "UNAVAILABLE" || e.message?.contains("offline") == true) {
                    delay(1000) // wait and retry
                    Log.w("UserRepository", "Attempt ${attempt + 1} to fetch user data failed: ${e.message}")
                } else {
                    return Result.failure(e)
                }
            }
        }

        return Result.failure(Exception("Failed to fetch user data after retries"))
    }

    suspend fun resendVerification(): Result<Boolean> {
        return try {
            val user = auth.currentUser ?: return Result.failure(Exception("User not logged in"))
            user.reload().await() // Reload latest data from Firebase
            if (user.isEmailVerified) {
                Result.failure(Exception("Email already verified"))
            } else {
                user.sendEmailVerification().await()
                Result.success(true)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    fun logout(){
        auth.signOut()
    }

    suspend fun resetPassword(email: String): Result<Boolean>{
        return try{
            val result = auth.sendPasswordResetEmail(email).await()
            Result.success(true)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun loginAsGuest(): Result<Boolean>{
        return try{
            auth.signInAnonymously().await()
            Result.success(true)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    fun isAnonymous(): Boolean {
            return auth.currentUser?.isAnonymous ?: false
    }

}
