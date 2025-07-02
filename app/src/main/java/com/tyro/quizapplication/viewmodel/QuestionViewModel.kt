package com.tyro.quizapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.quizapplication.data.entity.Question
import com.tyro.quizapplication.data.misc.QuizGraph
import com.tyro.quizapplication.data.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class QuestionViewModel(private val questionsRepository: QuestionRepository = QuizGraph.questionRepository): ViewModel() {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    private val _selectedAnswers = MutableStateFlow<Map<Int, String>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, String>> = _selectedAnswers

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _score = MutableStateFlow(0)
    val score : StateFlow<Int> = _score

    private val _correctAnswers = MutableStateFlow(0)
    val correctAnswers : StateFlow<Int> = _correctAnswers

    fun loadQuestions(difficulty: String, type: String, limit: Int = 15){
        viewModelScope.launch {
            _isLoading.value = true

            val all = questionsRepository.getLocalQuestions(difficulty, type)
            _questions.value = all.shuffled().take(limit)

            _isLoading.value = false
        }
    }

    fun syncQuestions(){
        viewModelScope.launch {
            _isLoading.value = true
            questionsRepository.syncFromFireStore()
            _isLoading.value = false
        }
    }
    fun selectAnswer(questionIndex: Int, answer: String) {
        _selectedAnswers.value = _selectedAnswers.value.toMutableMap().apply {
            this[questionIndex] = answer
        }
    }

    fun calculateScore(){
        val questionList = _questions.value
        val selected = _selectedAnswers.value

        _correctAnswers.value = questionList.withIndex().count { (index, question) ->
            selected[index] == question.options[question.correctAnswerIndex]
        }

        _score.value = ((_correctAnswers.value.toDouble()/questionList.size) * 100).toInt()
    }

}