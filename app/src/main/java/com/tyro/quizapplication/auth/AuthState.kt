package com.tyro.quizapplication.auth

sealed class AuthState {
    object Loading : AuthState()
    object LoggedOut : AuthState()
    object Unverified : AuthState()
    object Verified : AuthState()
    object Guest : AuthState()
}