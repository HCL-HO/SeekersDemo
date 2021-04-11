package com.example.seekersgroup.network

import com.example.seekersgroup.configuration.ConfigManager
import com.example.seekersgroup.model.RatePairsResp
import com.example.seekersgroup.model.RatesResp
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * {
"supportedPairs":[
"EURUSD",
"EURGBP",
"GBPUSD",
"USDJPY",
"AUDUSD",
"USDCHF",
"NZDUSD",
"USDCAD",
"USDZAR"
],
"message":"'pairs' parameter is required",
"code":1001
}
 */

//https://www.freeforexapi.com/Home/Api

interface ForexService {
    @GET("live")
    fun getSupportedPairsAsync(): Deferred<RatePairsResp>

    @GET("live")
    fun getByPairsAsync(@Query("pairs") pairs: String): Deferred<RatesResp>

}


object ForexAPIService {
    private var BASE_URL = ConfigManager.getForexURL()

    val retrofitService: ForexService by lazy {
        NetworkUtil.getRetrofitBuilder(
            BASE_URL
        ).create(ForexService::class.java)
    }
}
