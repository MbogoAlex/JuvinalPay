package com.juvinal.pay.container

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.juvinal.pay.db.AppDatabase
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.db.DBRepositoryImpl
import com.juvinal.pay.network.ApiRepository
import com.juvinal.pay.network.ApiRepositoryImpl
import com.juvinal.pay.network.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val apiRepository: ApiRepository
    val dbRepository: DBRepository
}

class AppContainerImpl(context: Context): AppContainer {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val baseUrl = "http://172.105.90.112/juvinal-backend/public/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val dbRepository: DBRepository by lazy {
        DBRepositoryImpl(AppDatabase.getDatabase(context).appDao())
    }

    override val apiRepository: ApiRepository by lazy {
        ApiRepositoryImpl(retrofitService, dbRepository)
    }

}