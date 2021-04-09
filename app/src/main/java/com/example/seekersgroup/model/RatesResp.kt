package com.example.seekersgroup.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class RatesResp(
    @SerializedName("rates") val rates: JsonObject? = null,
    @SerializedName("code") val code: Int? = null,
    @SerializedName("message") val message: String? = null,
)

/*
* {
    "rates":{
        "EURGBP":{
            "rate":0.891724,
            "timestamp":1532429549281
        },
        "USDJPY":{
            "rate":111.1307,
            "timestamp":1532429549281
        }
    },
    "code":200
}
*
* */