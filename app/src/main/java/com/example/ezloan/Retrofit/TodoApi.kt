package com.example.ezloan.Retrofit

import com.android.volley.Response
import com.example.ezloan.Data.APIRequestDataType
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TodoApi {

    @POST("/predict")
    suspend fun predict(@Body data : APIRequestDataType) : String

}
