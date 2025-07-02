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
import com.tyro.quizapplication.auth.AuthState
import com.tyro.quizapplication.data.misc.QuizGraph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await


class AuthViewModel(private val _userRepository: UserRepository = QuizGraph.userRepository): ViewModel() {

    private val _currentUser = MutableLiveData<Result<User>?>()
    val currentUser: MutableLiveData<Result<User>?> = _currentUser

    private val _currentUserDetails = MutableStateFlow(User())
    val currentUserDetails: StateFlow<User> = _currentUserDetails.asStateFlow()

    private val _authResult = MutableLiveData<Result<Boolean>?>()
    val authResult:LiveData<Result<Boolean>?> get() = _authResult

    private val _emailVerified = MutableLiveData<Result<Boolean>?>()
    val emailVerified: LiveData<Result<Boolean>?> get() = _emailVerified

    val isUserLoggedIn = mutableStateOf(FirebaseAuth.getInstance().currentUser != null)

    private val _isUserLoading = MutableStateFlow(false)
    val isUserLoading: StateFlow<Boolean> = _isUserLoading

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState : StateFlow<AuthState> = _authState

    private val _hasNavigated = MutableStateFlow(false)
    val hasNavigated: StateFlow<Boolean> = _hasNavigated

    init {
        checkAuthStatus()
    }

    fun hasNavigated(){
        _hasNavigated.value = true
    }

    fun resetHasNavigated(){
        _hasNavigated.value = false
    }

    fun checkAuthStatus(){
        val fireBaseUser = FirebaseAuth.getInstance().currentUser

        when{
            fireBaseUser == null -> {
                _authState.value = AuthState.LoggedOut
            }
            fireBaseUser.isAnonymous -> {
                _authState.value = AuthState.Guest
            }
            fireBaseUser.isEmailVerified -> {
                _authState.value = AuthState.Verified
            }
            else -> {
                _authState.value = AuthState.Unverified
            }
        }
    }

    fun signUp(email: String, password: String, surname: String, firstname: String){
        viewModelScope.launch {
            _isUserLoading.value = true

            val result = _userRepository.signUp(email, password, surname, firstname)
            _authResult.value = result
            if(result.isSuccess){
                isUserLoggedIn.value = false
                fetchCurrentUser()
            }
            _isUserLoading.value = false
        }
    }

    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            _isUserLoading.value = true
            val result = _userRepository.signIn(email, password)
            _authResult.value = result
            if(result.isSuccess){
                isUserLoggedIn.value = true
                fetchCurrentUser()
            }
            _isUserLoading.value = false
        }
    }
    fun checkEmailVerification(){
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = _userRepository.checkEmailVerified()

            _authState.value = when {
                FirebaseAuth.getInstance().currentUser?.isAnonymous == true -> AuthState.Guest
                result.isSuccess -> AuthState.Verified
                else -> AuthState.Unverified
            }
        }
    }

    fun fetchCurrentUser(){
        _isUserLoading.value = true
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
            _isUserLoading.value = false
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
        FirebaseAuth.getInstance().signOut()
        _authState.value = AuthState.LoggedOut

        _isUserLoading.value = true
        _userRepository.logout()
        isUserLoggedIn.value = false
        _currentUser.value = null
        _currentUserDetails.value = User()
        _emailVerified.value = null
        _authResult.value = null
        _isUserLoading.value = false
        _hasNavigated.value = false
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

    fun updateScoreAndQuiz(score: Long){
        viewModelScope.launch {
            _userRepository.updateScoreAndQuiz(score)
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
            val result = _userRepository.loginAsGuest()
            _authState.value = if (result.isSuccess) AuthState.Guest else AuthState.LoggedOut
        }
    }

    val isAnonymous: Boolean
        get() = _userRepository.isAnonymous()

}

