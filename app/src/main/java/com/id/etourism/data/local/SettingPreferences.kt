package com.id.etourism.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//class SettingsPreferences @Inject constructor(
//    @ApplicationContext val context: Context
//) {
//
//    private val dataStore: DataStore<Preferences>
//    private val THEME_KEY = booleanPreferencesKey("theme_setting")
//
//    fun getThemeSetting(): Flow<Boolean> {
//        return dataStore.data.map { preferences ->
//            preferences[THEME_KEY] ?: false
//        }
//    }
//
//    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
//        dataStore.edit { preferences ->
//            preferences[THEME_KEY] = isDarkModeActive
//        }
//    }
//
//
//}