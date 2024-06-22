package com.juvinal.pay

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.juvinal.pay.container.AppContainer
import com.juvinal.pay.container.AppContainerImpl
import com.juvinal.pay.datastore.DSRepository

private const val DS_NAME = "JUVINAL_PAY_DS"
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DS_NAME)
class JuvinalPay: Application() {
    lateinit var container: AppContainer
    lateinit var dsRepository: DSRepository

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
        dsRepository = DSRepository(dataStore = datastore)
    }

}