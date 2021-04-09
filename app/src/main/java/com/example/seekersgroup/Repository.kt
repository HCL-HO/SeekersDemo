package com.example.seekersgroup

import com.example.seekersgroup.model.RatesResp
import com.example.seekersgroup.network.ForexService
import kotlinx.coroutines.Deferred

class Repository(val forexService: ForexService) : IRepository {
    override suspend fun getSupportedPairsAsync(): Deferred<String> {
        return forexService.getSupportedPairsAsync()
    }

    override suspend fun getExchangeRatesAsync(keys: String): Deferred<RatesResp> {
        return forexService.getByPairsAsync(keys)
    }

}