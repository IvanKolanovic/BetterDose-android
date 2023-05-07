package com.ik3130.betterdose.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SaveLocalStore(private val context: Context) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userEmail")
        val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")
    }

    //get the saved email
    val getUISeenState: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_FIRST_TIME] ?: false
    }

    //save email into datastore
    suspend fun saveUIState(flag: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_FIRST_TIME] = flag
        }
    }


}