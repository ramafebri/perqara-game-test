package com.mandiri.gamesapp.data.base.common

import com.google.gson.Gson
import com.mandiri.gamesapp.data.base.response.ApiResponse
import com.mandiri.gamesapp.data.base.response.BaseErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

open class BaseServiceAPI {
    protected suspend fun <T> callApi(callApiFunction: suspend () -> Response<T>): Flow<ApiResponse<T>> {
        return try {
            callApiFunction.invoke().unfold()
        } catch (exception: Exception) {
            flow<ApiResponse<T>> {
                emit(ApiResponse.Error(exception))
            }.flowOn(Dispatchers.IO)
        }
    }

    private fun <T> Response<T>.unfold(): Flow<ApiResponse<T>> {
        return flow<ApiResponse<T>> {
            try {
                val response = this@unfold
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ApiResponse.Success(it))
                    }
                        ?: emit(ApiResponse.Error(Throwable(DEFAULT_ERROR_MESSAGE)))
                } else {
                    val message = try {
                        Gson().fromJson(
                            response.errorBody()?.string(),
                            BaseErrorResponse::class.java
                        ).error.toString()
                    } catch (e: Throwable) {
                        DEFAULT_ERROR_MESSAGE
                    }
                    emit(ApiResponse.Error(Throwable(message)))
                }
            } catch (exception: Exception) {
                emit(ApiResponse.Error(exception))
            }
        }.flowOn(Dispatchers.IO)
    }
    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Terjadi kesalah sistem"
    }
}