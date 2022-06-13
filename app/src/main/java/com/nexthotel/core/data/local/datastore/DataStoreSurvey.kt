package com.nexthotel.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStoreSurvey(private val dataStore: DataStore<Preferences>) {

    private object Keys {
        val survey = booleanPreferencesKey("is_survey")
    }

    suspend fun setIsSurvey(isSurvey: IsSurvey) {
        dataStore.edit { preferences ->
            preferences[Keys.survey] = when (isSurvey) {
                IsSurvey.TRUE -> true
                IsSurvey.FALSE -> false
            }
        }
    }

    val modeUIFlow: Flow<IsSurvey> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            when (preferences[Keys.survey] ?: false) {
                true -> IsSurvey.TRUE
                false -> IsSurvey.FALSE
            }
        }
    companion object {
        @Volatile
        private var INSTANCE: DataStoreSurvey? = null

        fun getInstance(dataStore: DataStore<Preferences>): DataStoreSurvey {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStoreSurvey(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}

enum class IsSurvey {
    TRUE, FALSE
}