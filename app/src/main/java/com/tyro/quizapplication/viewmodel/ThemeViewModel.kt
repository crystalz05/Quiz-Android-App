package com.tyro.quizapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.quizapplication.data.misc.ThemeMode
import com.tyro.quizapplication.preferences_pk.ThemePreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode

    fun toggleDarkLight() {
        _themeMode.value = when (_themeMode.value) {
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.SYSTEM -> ThemeMode.LIGHT // fallback
        }
    }

    fun useSystemTheme() {
        _themeMode.value = ThemeMode.SYSTEM
    }

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

//    private val _themePreference = ThemePreference(application)
//    val isDarkTheme: StateFlow<Boolean> =
//        _themePreference.isDarkTheme.stateIn(viewModelScope, SharingStarted.Eagerly, false)
//
//    fun toggleTheme() {
//        viewModelScope.launch {
//            _themePreference.saveThemePreference(!isDarkTheme.value)
//        }
//    }
}