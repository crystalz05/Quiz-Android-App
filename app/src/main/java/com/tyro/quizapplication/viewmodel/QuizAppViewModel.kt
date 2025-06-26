package com.tyro.quizapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class QuizAppViewModel(): ViewModel() {

    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var surname by mutableStateOf("")
    var firstName by mutableStateOf("")
    var email by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)


    fun clearFields() {
        email = ""
        password = ""
        confirmPassword = ""
        surname = ""
        firstName = ""
    }
}
