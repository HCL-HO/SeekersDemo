package com.sinoway.smart.properties.propertyraptor.configuration

import android.content.Context
import android.content.Context.MODE_PRIVATE

object PreferenceManager {
    const val KEY_CONTACT_SEARCH = "KEY-CON-SEARCH"
    const val KEY_LEAD_SEARCH = "KEY-LEAD-SEARCH"
    const val KEY_PR_COMMON_PREF = "PR-PREF"
    const val KEY_PRIVACY_POLICY = "KEY-PP"
    const val KEY_SET_FACEID = "KEY-FACE"
    const val KEY_SET_TOUCHID = "KEY-TOUCH"
    const val KEY_SET_NETWORK = "KEY-NETWORK"
    const val KEY_LOGIN_NAME = "KEY-LOGININNAME"
    const val SEPERATOR_CONTACT = ";"

    fun hasAgreedToPrivacyPolicy(context: Context): Boolean {
        return context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE)
            .getBoolean(KEY_PRIVACY_POLICY, false)
    }

    fun setAgreedPrivacyPolicy(context: Context, agreed: Boolean = true) {
        context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE).edit().putBoolean(
            KEY_PRIVACY_POLICY, agreed
        ).apply()
    }

    fun saveLastLoginName(context: Context, name: String) {
        context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE).edit().putString(
            KEY_LOGIN_NAME, name
        ).apply()
    }

    fun getLastLoginName(context: Context): String? {
        return context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE)
            .getString(KEY_LOGIN_NAME, "")
    }

    fun isFaceIDChecked(context: Context): Boolean {
        return context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE)
            .getBoolean(KEY_SET_FACEID, false)
    }

    fun isTouchIDChecked(context: Context): Boolean {
        return context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE)
            .getBoolean(KEY_SET_TOUCHID, false)
    }

    fun saveFaceIDChecked(context: Context, boolean: Boolean) {
        context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE).edit().putBoolean(
            KEY_SET_FACEID, boolean
        ).apply()
    }

    fun saveTouchIDChecked(context: Context, boolean: Boolean) {
        context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE).edit().putBoolean(
            KEY_SET_TOUCHID, boolean
        ).apply()
    }


    fun saveNetworkPaths(context: Context, json: String) {
        context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE).edit().putString(
            KEY_SET_NETWORK, json
        ).apply()
    }

    fun getNetWorkPaths(context: Context): String {
        return context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE)
            .getString(KEY_SET_NETWORK, "") ?: ""
    }

    fun getSearchHistoryList(context: Context, key: String): List<String> {
        val store = context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE)
            .getString(key, "") ?: ""
        return store.split(SEPERATOR_CONTACT)
    }

    fun addSearchHistory(context: Context, text: String, key: String) {
        val hist = getSearchHistoryList(context, key).toMutableList()
        if (!hist.contains(text)) {
            hist.add(text)
        }
        val updated = hist.reduce { acc, s ->
            acc + SEPERATOR_CONTACT + s
        }
        context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE).edit().putString(
            KEY_LEAD_SEARCH, updated
        ).apply()
    }

    fun cleanSearchHistory(context: Context, key: String) {
        context.getSharedPreferences(KEY_PR_COMMON_PREF, MODE_PRIVATE).edit().putString(
            key, ""
        ).apply()
    }

}