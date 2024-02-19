package com.eunji.lookatthis.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserDataSourceLocal @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val BASIC_TOKEN = stringPreferencesKey("basicToken")

    suspend fun saveBasicToken(basicToken: String) {
        dataStore.edit { prefs ->
            prefs[BASIC_TOKEN] = basicToken
        }
    }

    fun getBasicToken(): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[BASIC_TOKEN]
            }
    }

    suspend fun deleteBasicToken() {
        dataStore.edit { prefs ->
            if (prefs.contains(BASIC_TOKEN)) prefs.remove(BASIC_TOKEN)
        }
    }
}