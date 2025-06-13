package com.tyro.quizapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tyro.quizapplication.data.repository.UserRepository
import com.tyro.quizapplication.injection.Injection
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val _userRepository: UserRepository
    private val _authResult = MutableLiveData<Result<Boolean>?>()
    val authResult:LiveData<Result<Boolean>?> get() = _authResult

    init{
        _userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }

    fun signUp(email: String, password: String, surname: String, firstname: String){
        viewModelScope.launch {
            _authResult.value = _userRepository.signUp(email, password, surname, firstname)
        }
    }

    fun logIn(email: String, password: String){
        viewModelScope.launch {
            _authResult.value = _userRepository.signIn(email, password)
        }
    }

}