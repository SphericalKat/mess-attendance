package org.firehound.messattendance.models

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("/getcsv")
    suspend fun getCsv(@Body body: ArrayList<MessEntry>): Response<String>
}