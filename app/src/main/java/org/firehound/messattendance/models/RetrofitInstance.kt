package org.firehound.messattendance.models

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {
    private const val BASE_URL = "https://json-csv.com/api"

    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}