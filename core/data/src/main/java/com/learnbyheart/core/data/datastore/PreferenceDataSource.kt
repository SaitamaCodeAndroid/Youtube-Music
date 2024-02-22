package com.learnbyheart.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.learnbyheart.core.model.BearerToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PREFERENCE_ACCESS_TOKEN = "access_token"
private const val PREFERENCE_EXPIRED_TIME = "duration"

class PreferenceDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val accessTokenKey = stringPreferencesKey(PREFERENCE_ACCESS_TOKEN)
    private val expiredTimeKey = intPreferencesKey(PREFERENCE_EXPIRED_TIME)

    fun getAccessToken(): Flow<BearerToken> = dataStore.data
        .map { preference ->
            BearerToken(
                bearerToken = preference[accessTokenKey] ?: "",
                expirationTimeInMillis = preference[expiredTimeKey] ?: 0,
            )
        }

    suspend fun saveAccessToken(
        token: String,
        expiredTime: Int,
    ) {
        dataStore.edit { preference ->
            preference[accessTokenKey] = token
            preference[expiredTimeKey] = expiredTime
        }
    }
}
