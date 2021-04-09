package com.example.seekersgroup.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.seekersgroup.configuration.ConfigManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object NetworkUtil {

    const val HEADER_JSON_CONTENT = "Content-Type: application/json"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val logger = HttpLoggingInterceptor().apply {
        level =
            ConfigManager.getHttpLogLevel()
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
//        .addInterceptor { chain ->
//            val request = chain.request()
//            val response = chain.proceed(request)
//            if (response.body.toString() == "401") {
//            }
//            response
//        }
        .build()

    fun getRetrofitBuilder(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

}
