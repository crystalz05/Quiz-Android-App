package com.tyro.quizapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.quizapplication.data.entity.Question
import com.tyro.quizapplication.data.entity.Quiz
import com.tyro.quizapplication.data.misc.QuizGraph
import com.tyro.quizapplication.data.repository.QuestionRepository
import com.tyro.quizapplication.data.repository.QuizRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(private val quizRepository: QuizRepository = QuizGraph.quizRepository): ViewModel() {


    private val _quizTimeLeft = MutableStateFlow<Long>(300L)
    val quizTimeLeft: StateFlow<Long> = _quizTimeLeft

    private val _quizStart = MutableStateFlow<Boolean> (false)
    val quizStart : StateFlow<Boolean> = _quizStart

    private val _exitQuiz = MutableStateFlow<Boolean> (false)
    val exitQuiz : StateFlow<Boolean> = _exitQuiz

    private val _currentQuiz = MutableStateFlow<Quiz>(Quiz())
    val currentQuiz : StateFlow<Quiz> = _currentQuiz

    private var timerJob: Job? = null


    fun toggleExitQuiz(toggle: Boolean){
        _exitQuiz.value = toggle
    }

    fun createQuiz(difficulty: String, type: String, questions: List<Question>){
        viewModelScope.launch {
            _currentQuiz.value = quizRepository.createQuiz(difficulty, type, questions)
        }
    }


    fun startQuiz(onTimeOut: () -> Unit){
        // Cancel any previous running timer
        timerJob?.cancel()

        timerJob = viewModelScope.launch {

            while(_quizTimeLeft.value > 0){
                delay(1000L)
                _quizTimeLeft.value--
            }
            onTimeOut()
        }
    }

    fun endQuiz(){
        viewModelScope.launch {
            timerJob?.cancel()
            _quizTimeLeft.value = 300L
        }
    }

}

