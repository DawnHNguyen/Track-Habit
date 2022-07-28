package com.track.common.base.data.remote.util

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonPrimitive
import okhttp3.Request
import okhttp3.ResponseBody
import okio.IOException
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.net.ssl.SSLHandshakeException

@Suppress("ThrowableNotThrown")
class CallAdapterFactory private constructor() : CallAdapter.Factory() {
    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun invoke() = CallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        check (returnType is ParameterizedType) {
            throw IllegalStateException(
                "Response return type must be parameterized as Call<Resource<Foo>> or Call<Resource<out Foo>>")
        }

        val responseType = getParameterUpperBound(0, returnType)

        if (getRawType(responseType) != Resource::class.java) {
            return null
        }

        check (responseType is ParameterizedType) {
            "Response must be parameterized as Resource<Foo> or Resource<out Foo>"
        }

        val successBodyType = getParameterUpperBound(0, responseType)

        val converter = retrofit.nextResponseBodyConverter<Resource<Any>>(null, successBodyType, annotations)

        return BodyCallAdapter(successBodyType, converter)
    }

    private class BodyCallAdapter<T : Any>(
        private val responseType: Type,
        private val converter: Converter<ResponseBody, Resource<T>>
    ) : CallAdapter<T, Call<Resource<T>>> {

        override fun responseType() : Type = responseType

        override fun adapt(call: Call<T>): Call<Resource<T>> {
            return ResourceCall(call, converter)
        }
    }

    internal class ResourceCall<S: Any>(private val delegate: Call<S>, private val converter: Converter<ResponseBody, Resource<S>>) :
        Call<Resource<S>> {
        override fun enqueue(callback: Callback<Resource<S>>) {

            delegate.enqueue(object : Callback<S> {
                override fun onFailure(call: Call<S>, t: Throwable) {
                    val apiResponse = when (t) {
                        is IOException, is SSLHandshakeException -> Resource.error(
                            NoNetworkException("No network connection"),
                            null
                        )
                        //SSLHandshakeException is thrown when user's internet connection is disconnected
                        //before the server can return response
                        else -> {
                            Resource.error(UnknownException("Unknown exception"), null)
                        }
                    }
                    callback.onResponse(this@ResourceCall, Response.success(apiResponse))
                }

                override fun onResponse(call: Call<S>, response: Response<S>) {
                    val body = response.body()
                    val code = response.code()
                    val errorBody = response.errorBody()?.string().orEmpty()

                    if (response.isSuccessful && response.body() != null) {
                        callback.onResponse(this@ResourceCall, Response.success(
                            Resource.success(
                                body!!
                            )
                        ))
                    } else {
                        val gson = Gson()
                        val message = try {
                            val errorResponse = gson.fromJson(errorBody, BaseErrorResponse::class.java).message
                            when (errorResponse) {
                                is JsonPrimitive -> if(errorResponse.isString) errorResponse.asString else ""
                                is JsonArray -> {
                                    if (errorResponse.size() > 0 && errorResponse.get(0).isJsonPrimitive) {
                                        val errorResponsePrimitive = errorResponse.get(0) as JsonPrimitive
                                        if (errorResponsePrimitive.isString) errorResponsePrimitive.asString.replaceFirstChar { it.uppercase() } else ""
                                    } else ""
                                }
                                else -> ""
                            }
                        } catch (e: Exception) {
                            ""
                        }
                        val exception = when (code) {
                            400 -> BadRequestException(message)
                            401,403 -> NetworkAuthenticationException(message)
                            404 -> NetworkResourceNotFoundException(message)
                            408 -> RequestTimeoutException(message)
                            500 -> NetworkServerException(null)
                            else -> NetworkException()
                        }

                        callback.onResponse(this@ResourceCall, Response.success(
                            Resource.error(
                                exception,
                                null
                            )
                        ))
                    }
                }
            })
        }

        override fun isExecuted(): Boolean = delegate.isExecuted

        override fun timeout(): Timeout {
            return delegate.timeout()
        }

        override fun clone(): Call<Resource<S>> = ResourceCall(delegate.clone(), converter)

        override fun isCanceled(): Boolean = delegate.isCanceled

        override fun cancel() = delegate.cancel()

        override fun execute(): Response<Resource<S>> {
            throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
        }

        override fun request(): Request = delegate.request()
    }
}