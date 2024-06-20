package com.juvinal.pay.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DSRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        private val ID = intPreferencesKey("id")
        private val SURNAME = stringPreferencesKey("surname")
        private val FNAME = stringPreferencesKey("fname")
        private val LNAME = stringPreferencesKey("lname")
        private val DOCUMENT_TYPE = stringPreferencesKey("document_type")
        private val DOCUMENT_NO = stringPreferencesKey("document_no")
        private val EMAIL = stringPreferencesKey("email")
        private val PHONE_NO = stringPreferencesKey("phone_no")
        private val CREATED_AT = stringPreferencesKey("created_at")
        private val UPDATED_AT = stringPreferencesKey("updated_at")
        private val PASSWORD = stringPreferencesKey("password")
        private val NAME = stringPreferencesKey("name")
        private val UID = stringPreferencesKey("uid")
    }

    suspend fun saveUserDetails(
        userDSModel: UserDSModel
    ) {
        dataStore.edit { preferences ->
            preferences[ID] = userDSModel.id!!
            preferences[SURNAME] = userDSModel.surname
            preferences[FNAME] = userDSModel.fname
            preferences[LNAME] = userDSModel.lname
            preferences[DOCUMENT_TYPE] = userDSModel.document_type
            preferences[DOCUMENT_NO] = userDSModel.document_no
            preferences[EMAIL] = userDSModel.email
            preferences[PHONE_NO] = userDSModel.phone_no
            preferences[CREATED_AT] = userDSModel.created_at
            preferences[UPDATED_AT] = userDSModel.updated_at
            preferences[PASSWORD] = userDSModel.password
            preferences[NAME] = userDSModel.name
            preferences[UID] = userDSModel.uid
        }
    }

    val userDSDetails: Flow<UserDSModel> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it.toUserDSModel()
        }

    private fun Preferences.toUserDSModel(): UserDSModel = UserDSModel(
        id = this[ID],
        surname = this[SURNAME] ?: "",
        fname = this[FNAME] ?: "",
        lname = this[LNAME] ?: "",
        document_type = this[DOCUMENT_TYPE] ?: "",
        document_no = this[DOCUMENT_NO] ?: "",
        email = this[EMAIL] ?: "",
        phone_no = this[PHONE_NO] ?: "",
        created_at = this[CREATED_AT] ?: "",
        updated_at = this[UPDATED_AT] ?: "",
        password = this[PASSWORD] ?: "",
        name = this[NAME] ?: "",
        uid = this[UID] ?: ""
    )

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}