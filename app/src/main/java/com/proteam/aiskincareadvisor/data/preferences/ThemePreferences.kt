package com.proteam.aiskincareadvisor.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

class ThemePreferences(private val context: Context) {
    
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_preferences")
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        private val USE_DYNAMIC_COLORS_KEY = booleanPreferencesKey("use_dynamic_colors")
    }
    
    val themeMode: Flow<ThemeMode> = context.dataStore.data.map { preferences ->
        val themeModeString = preferences[THEME_MODE_KEY] ?: ThemeMode.SYSTEM.name
        try {
            ThemeMode.valueOf(themeModeString)
        } catch (e: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }
    
    val useDynamicColors: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[USE_DYNAMIC_COLORS_KEY] ?: true
    }
    
    suspend fun setThemeMode(themeMode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode.name
        }
    }
    
    suspend fun setUseDynamicColors(useDynamicColors: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USE_DYNAMIC_COLORS_KEY] = useDynamicColors
        }
    }
}