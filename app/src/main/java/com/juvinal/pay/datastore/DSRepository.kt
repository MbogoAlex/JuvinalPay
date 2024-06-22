package com.juvinal.pay.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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
        private val MEMBER_NO = stringPreferencesKey("member_no")
        private val MEMBER_JOINED_DATE = stringPreferencesKey("member_joined_date")
        private val MEMBER_STATUS = intPreferencesKey("member_status")
        private val MEMBER_REGISTERED = booleanPreferencesKey("member_registered")
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
        private val MEMBER_FEE_PAYMENT_REFERENCE = stringPreferencesKey("member_fee_payment_reference")
        private val DEPOSIT_PAYMENT_REFERENCE = stringPreferencesKey("deposit_payment_reference")
    }

    suspend fun saveUserDetails(
        userDSModel: UserDSModel,
    ) {
        dataStore.edit { preferences ->
            preferences[ID] = userDSModel.id!!
            preferences[MEMBER_NO] = userDSModel.mem_no ?: ""
            preferences[MEMBER_JOINED_DATE] = userDSModel.mem_joined_date ?: ""
            preferences[MEMBER_STATUS] = userDSModel.mem_status ?: 0
            preferences[MEMBER_REGISTERED] = userDSModel.mem_registered
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

    suspend fun savePaymentData(paymentReferenceDSModel: PaymentReferenceDSModel) {
        dataStore.edit { preferences ->
            preferences[MEMBER_FEE_PAYMENT_REFERENCE] = paymentReferenceDSModel.memberFeePaymentReference ?: ""
            preferences[DEPOSIT_PAYMENT_REFERENCE] = paymentReferenceDSModel.depositPaymentReference ?: ""
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
        mem_no = this[MEMBER_NO] ?: "",
        mem_joined_date = this[MEMBER_JOINED_DATE] ?: "",
        mem_status = this[MEMBER_STATUS],
        mem_registered = this[MEMBER_REGISTERED] ?: false,
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

    val paymentDSDetails: Flow<PaymentReferenceDSModel> = dataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it.toPaymentReferenceDSModel()
        }

    private fun Preferences.toPaymentReferenceDSModel(): PaymentReferenceDSModel = PaymentReferenceDSModel(
        memberFeePaymentReference = this[MEMBER_FEE_PAYMENT_REFERENCE],
        depositPaymentReference = this[DEPOSIT_PAYMENT_REFERENCE]
    )

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}