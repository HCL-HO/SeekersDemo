package com.example.seekersgroup.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.seekersgroup.configuration.ConfigManager
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object NetworkUtil {

    internal object JSONObjectAdapter {

        @FromJson
        fun fromJson(reader: JsonReader): JSONObject? {
            // Here we're expecting the JSON object, it is processed as Map<String, Any> by Moshi
            return (reader.readJsonValue() as? Map<*, *>)?.let { data ->
                try {
                    JSONObject(data)
                } catch (e: JSONException) {
                    // Handle error if arises
                    JSONObject()
                }
            }
        }

        @ToJson
        fun toJson(writer: JsonWriter, value: JSONObject?) {
            value?.let { writer.value(Buffer().writeUtf8(value.toString())) }
        }
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(JSONObjectAdapter)
        .build()
    private val logger = HttpLoggingInterceptor().apply {
        level =
            ConfigManager.getHttpLogLevel()
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
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
