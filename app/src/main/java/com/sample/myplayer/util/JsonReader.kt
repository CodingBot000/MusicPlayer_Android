package com.sample.myplayer.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

interface JsonReaderApi {
    suspend fun jsonUrlReader(
        jsonUrl: String
    ): String
}
class JsonReaderApiImpl @Inject constructor(
    private val okHttpClient: OkHttpClient
) : JsonReaderApi {

    override suspend fun jsonUrlReader(
        jsonUrl: String,
    ): String = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(jsonUrl)
            .build()

        val response = okHttpClient.newCall(request).execute()

        if (response.isSuccessful) {
            return@withContext response.body?.string()!!
        }

        throw ServerError(response.code.toString(), response.body?.toString() ?: "")
    }
}
