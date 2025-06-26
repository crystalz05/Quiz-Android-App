package com.tyro.quizapplication.preferences_pk

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")
class ThemePreference(
    private val context: Context
) {

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data.map {
        preferences -> preferences[DARK_MODE_KEY] ?: false
    }

    suspend fun saveThemePreference(isDark: Boolean){
        context.dataStore.edit {
            preferences -> preferences[DARK_MODE_KEY] = isDark
        }
    }

}