package com.tyro.quizapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tyro.quizapplication.data.entity.User
import com.tyro.quizapplication.data.repository.UserRepository
import com.tyro.quizapplication.injection.Injection
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await


class AuthViewModel(): ViewModel() {

    private val _userRepository: UserRepository

    private val _currentUser = MutableLiveData<Result<User>?>()
    val currentUser: MutableLiveData<Result<User>?> = _currentUser

    private val _currentUserDetails = MutableStateFlow(User())
    val currentUserDetails: StateFlow<User> = _currentUserDetails.asStateFlow()


    private val _authResult = MutableLiveData<Result<Boolean>?>()
    val authResult:LiveData<Result<Boolean>?> get() = _authResult

    private val _emailVerified = MutableLiveData<Result<Boolean>?>()
    val emailVerified: LiveData<Result<Boolean>?> get() = _emailVerified

    val isUserLoggedIn = mutableStateOf(FirebaseAuth.getInstance().currentUser != null)

    val isLoading = mutableStateOf(false)

    init{
        _userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }

    fun signUp(email: String, password: String, surname: String, firstname: String){
        viewModelScope.launch {
            isLoading.value = true

            val result = _userRepository.signUp(email, password, surname, firstname)
            _authResult.value = result
            if(result.isSuccess){
                isUserLoggedIn.value = false
                fetchCurrentUser()
            }
            isLoading.value = false
        }
    }

    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            val result = _userRepository.signIn(email, password)
            _authResult.value = result
            if(result.isSuccess){
                isUserLoggedIn.value = true
                fetchCurrentUser()
            }
            isLoading.value = false
        }
    }
    fun checkEmailVerification(){
        viewModelScope.launch {
            val result = _userRepository.checkEmailVerified()
            _emailVerified.value = result

            if (result.isSuccess) {
                isUserLoggedIn.value = true
                fetchCurrentUser()
            }
        }
    }

    fun fetchCurrentUser(){
        viewModelScope.launch {
            val result = _userRepository.getCurrentUser()
            if (result.isSuccess) {
                val user = result.getOrNull()
                user?.let {
                    updateCurrentUser(it)
                }
            } else {
                val exception = result.exceptionOrNull()
                Log.e("AuthViewModel", "Failed to fetch user", exception)
            }
        }
    }


    private fun updateCurrentUser(user: User) {
        _currentUserDetails.value = user
    }

    fun resendVerificationEmail(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ){
        viewModelScope.launch {
            val result = _userRepository.resendVerification()
            if (result.isSuccess) {
                onSuccess()
            }else {
                onFailure(result.exceptionOrNull() as? Exception ?: Exception("Unknown error"))
            }
        }
    }

    fun logout(){
        isLoading.value = true
        _userRepository.logout()
        isUserLoggedIn.value = false
        _currentUser.value = null
        _currentUserDetails.value = User()
        _emailVerified.value = null
        _authResult.value = null
        isLoading.value = false

    }

    fun resetPassword(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ){
        viewModelScope.launch {
            val result = _userRepository.resetPassword(email)
            if(result.isSuccess){
                onSuccess()
            }else{
                onFailure(result.exceptionOrNull() as? Exception ?: Exception("Unknown error"))
            }
        }
    }

    fun clearAuthResult() {
        _authResult.value = null
    }

    fun clearEmailVerified() {
        _emailVerified.value = null
    }

    fun loginAsGuest(){
        viewModelScope.launch {
            isLoading.value = true

            val result = _userRepository.loginAsGuest()
            _authResult.value = result

            if(result.isSuccess){
                isUserLoggedIn.value = true
                fetchCurrentUser()
            }
            isLoading.value = false
        }
    }

    val isAnonymous: Boolean
        get() = _userRepository.isAnonymous()

}

