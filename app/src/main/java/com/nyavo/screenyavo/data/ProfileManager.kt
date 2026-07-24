package com.nyavo.screenyavo.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    val userName: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_NAME_KEY] ?: ""
    }

    suspend fun updateUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }
}
