package com.sinoway.smart.properties.propertyraptor.network

import com.example.seekersgroup.network.NetworkUtil
import com.example.seekersgroup.configuration.ConfigManager.getTestAPI
import com.sinoway.smart.properties.propertyraptor.model.dto.TestAlbumResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface TestService {
    @GET("search")
    fun searchAsync(
        @Query("term") term: String,
        @Query("entity") entity: String,
    ): Deferred<TestAlbumResponse>
}

object TestServiceApi {
    private var BASE_URL = getTestAPI()
    val retrofitService: TestService by lazy {
        NetworkUtil.getRetrofitBuilder(
            BASE_URL
        )
            .create(TestService::class.java)
    }
}