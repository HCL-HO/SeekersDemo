package com.example.seekersgroup.configuration

import okhttp3.logging.HttpLoggingInterceptor
import razerdp.library.BuildConfig

object ConfigManager {

    fun getHttpLogLevel(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    fun getForexURL(): String {
        return "https://www.freeforexapi.com/api/live"
    }

}