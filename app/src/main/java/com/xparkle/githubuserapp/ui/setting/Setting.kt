package com.xparkle.githubuserapp.ui.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Setting private constructor(private val dataStore: DataStore<Preferences>){
    private val theme = intPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[theme] ?: 0
        }
    }

    suspend fun saveThemeSetting(themeMode: Int) {
        dataStore.edit { preferences ->
            preferences[theme] = themeMode
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Setting? = null

        fun getInstance(dataStore: DataStore<Preferences>): Setting {
            return INSTANCE ?: synchronized(this) {
                val instance = Setting(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}