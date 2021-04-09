package com.example.seekersgroup.util

import com.example.seekersgroup.model.Failure
import com.example.seekersgroup.model.SGResult
import com.example.seekersgroup.model.Success
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import timber.log.Timber

object DeferredExtension {
    suspend fun <T> Deferred<T>.awaitCatchException(): SGResult<T, Exception> {
        return try {
            val responseObject = this.await()
            Success(responseObject)
        } catch (httpException: HttpException) {
            Timber.e(httpException)
            Failure(httpException)
        } catch (exception: Exception) {
            Timber.e(exception)
            Failure(exception)
        }
    }
}