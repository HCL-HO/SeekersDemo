package com.example.seekersgroup.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class RatePairsResp(
    @SerializedName("supportedPairs") val supportedPairs: Array<String> = arrayOf(),
    @SerializedName("message") val message: String? = null,
    @SerializedName("code") val code: Int? = null,
)