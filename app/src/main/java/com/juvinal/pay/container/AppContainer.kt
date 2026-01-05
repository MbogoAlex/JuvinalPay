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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {
    val apiRepository: ApiRepository
    val dbRepository: DBRepository
}

class AppContainerImpl(context: Context): AppContainer {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val baseUrl = "https://juvinalpay.co.ke/juvinalpay/public/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(createOkHttpClient())
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    // Create OkHttpClient with custom timeouts
    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }

        return OkHttpClient.Builder()
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }

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