package com.track.common.base.data.remote.util

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.Response
import java.net.SocketTimeoutException
import kotlin.coroutines.coroutineContext

abstract class NetworkBoundResource<ResultType, RequestType> {
    private val result = MutableLiveData<Resource<ResultType>>()
    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        withContext(Dispatchers.Main){
            result.value = Resource.loading(null)
        }

        CoroutineScope(coroutineContext).launch(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork(dbResult)
                } catch (exception: SocketTimeoutException) {
                    result.value =
                        Resource.error(RequestTimeoutException(exception.message), loadFromDb())
                } catch (e: Exception) {
                    setValue(Resource.error(e, loadFromDb()))
                }
            } else {
                setValue(Resource.success(dbResult))
            }
        }

        return this
    }

    private suspend fun fetchFromNetwork(dbResult: ResultType?) {
        setValue(Resource.loading(dbResult)) // Dispatch latest value quickly (UX purpose)
        val apiResponse = createCallAsync()
        if (apiResponse.isSuccessful) {
            saveCallResults(processResponse(apiResponse))
            setValue(Resource.success(loadFromDb()))
        } else {
            setValue(handleNetworkError(apiResponse))
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) result.postValue(newValue)
    }

    @MainThread
    private suspend fun handleNetworkError(response: Response<RequestType>): Resource<ResultType> {
        val errorBody = response.errorBody().toString()

        val exception = when (response.code()) {
            400 -> BadRequestException(errorBody)
            401 -> NetworkAuthenticationException(errorBody)
            404 -> NetworkResourceNotFoundException(errorBody)
            500 -> NetworkServerException(errorBody)
            else -> Exception(errorBody)
        }

        return Resource.error(exception, loadFromDb())
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected abstract fun processResponse(response: Response<RequestType>): ResultType

    @WorkerThread
    protected abstract suspend fun saveCallResults(items: ResultType)

    @MainThread
    protected abstract suspend fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType?

    @MainThread
    protected abstract suspend fun createCallAsync(): Response<RequestType>
}