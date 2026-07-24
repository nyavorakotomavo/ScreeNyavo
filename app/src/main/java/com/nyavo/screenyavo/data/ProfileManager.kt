package com.nyavo.screenyavo.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "screenyavo_prefs")

class ProfileManager(private val context: Context) {
    companion object {
        private val DEAD_ZONE_MAP_KEY = stringPreferencesKey("dead_zone_map")
    }

    val deadZoneMapStream: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[DEAD_ZONE_MAP_KEY] ?: ""
    }

    async fun saveDeadZoneMap(jsonMap: String) {
        context.dataStore.edit { prefs ->
            prefs[DEAD_ZONE_MAP_KEY] = jsonMap
        }
    }
}
