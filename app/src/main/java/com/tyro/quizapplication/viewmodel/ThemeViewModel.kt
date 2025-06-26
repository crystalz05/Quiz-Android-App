package com.tyro.quizapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.quizapplication.preferences_pk.ThemePreference
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val _themePreference = ThemePreference(application)
    val isDarkTheme: StateFlow<Boolean> =
        _themePreference.isDarkTheme.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleTheme() {
        viewModelScope.launch {
            _themePreference.saveThemePreference(!isDarkTheme.value)
        }
    }
}