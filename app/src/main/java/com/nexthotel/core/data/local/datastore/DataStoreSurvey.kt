package com.nexthotel.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

enum class IsSurvey { TRUE, FALSE }

class DataStoreSurvey(private val dataStore: DataStore<Preferences>) {

    private object Keys {
        val survey = booleanPreferencesKey("is_survey")
        val recommendation = stringSetPreferencesKey("recommendation")
    }

    suspend fun setIsSurvey(isSurvey: IsSurvey) {
        dataStore.edit {
            it[Keys.survey] = when (isSurvey) {
                IsSurvey.TRUE -> true
                IsSurvey.FALSE -> false
            }
        }
    }

    suspend fun setRecommendation(recommendation: Set<String>) {
        dataStore.edit { it[Keys.recommendation] = recommendation }
    }

    val recommendationFlow: Flow<Set<String>> = dataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { it[Keys.recommendation] ?: setOf() }

    val modeUIFlow: Flow<IsSurvey> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            when (it[Keys.survey] ?: false) {
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
