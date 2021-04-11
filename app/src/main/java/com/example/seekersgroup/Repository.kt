package com.example.seekersgroup

import com.example.seekersgroup.model.RatePairsResp
import com.example.seekersgroup.model.RatesResp
import com.example.seekersgroup.network.ForexAPIService
import com.example.seekersgroup.network.ForexService
import kotlinx.coroutines.Deferred
import kotlin.reflect.KProperty

class Repository private constructor(val forexService: ForexService) : IRepository {

    override suspend fun getSupportedPairsAsync(): Deferred<RatePairsResp> {
        return forexService.getSupportedPairsAsync()
    }

    override suspend fun getExchangeRatesAsync(keys: String): Deferred<RatesResp> {
        return forexService.getByPairsAsync(keys)
    }

    companion object {
        fun get() : Repository{
            return Repository(ForexAPIService.retrofitService)
        }
    }

}

