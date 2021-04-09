package com.example.seekersgroup

import com.example.seekersgroup.model.RatesResp
import kotlinx.coroutines.Deferred

interface IRepository {
    suspend fun getSupportedPairsAsync(): Deferred<String>
    suspend fun getExchangeRatesAsync(keys: String): Deferred<RatesResp>
}
