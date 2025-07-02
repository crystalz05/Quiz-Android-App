package com.tyro.quizapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.quizapplication.data.entity.QuizResult
import com.tyro.quizapplication.data.misc.QuizGraph
import com.tyro.quizapplication.data.repository.QuizRepository
import com.tyro.quizapplication.data.repository.QuizResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizResultViewModel(
    private val quizResultRepository: QuizResultRepository
    = QuizGraph.quizResultRepository): ViewModel()
{

    private val _savedResult = MutableStateFlow<Result<Boolean>?>(null)
    val savedResult: StateFlow<Result<Boolean>?> = _savedResult

    private val _isSaving = MutableStateFlow(false)
    val isSaving : StateFlow<Boolean> = _isSaving

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    private val _quizResults = MutableStateFlow<List<QuizResult>>(emptyList())
    val quizResult : StateFlow<List<QuizResult>> = _quizResults


    fun saveQuizResult(userId: String, quizId: String, quizType: String, score: Int, totalQuestions: Int, correctAnswers: Int){
        viewModelScope.launch {
            _isSaving.value = true
            val result = quizResultRepository.saveQuizResult(
                userId = userId,
                quizId = quizId,
                quizType = quizType,
                score = score,
                totalQuestions = totalQuestions,
                correctAnswers = correctAnswers
            )
            _savedResult.value = result
            _isSaving.value = false
        }
    }

    fun getQuizResultsById(userId: String){
        viewModelScope.launch {
            _isLoading.value = true

            val result = quizResultRepository.getQuizResultsByUserId(userId)
            if(result.isSuccess){
                _quizResults.value = result.getOrDefault(emptyList())
            }else{
                _quizResults.value = emptyList()
            }
            _isLoading.value = false
        }
    }

    fun clearResults(){
        _savedResult.value = null
        _quizResults.value = emptyList()
    }

}


