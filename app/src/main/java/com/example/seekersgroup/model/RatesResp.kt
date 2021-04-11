package com.example.seekersgroup.model

import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.math.BigDecimal

data class RatesResp(
    @SerializedName("rates") val rates: JSONObject? = null,
    @SerializedName("code") val code: Int? = null,
    @SerializedName("message") val message: String? = null,
)

data class Rate(
    @SerializedName("rate") val rate: Double? = null,
    @SerializedName("timestamp") val timestamp: Long? = null,
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