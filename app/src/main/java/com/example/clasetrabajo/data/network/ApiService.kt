package com.example.clasetrabajo.data.network

import com.example.clasetrabajo.data.model.AccountModel
import com.example.clasetrabajo.data.model.UserModel
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field
import retrofit2.Response
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    @POST("user.php") //POST login
    suspend fun login(@Body user: UserModel): Response<JsonObject>

    @GET("service.php") //get all accounts
    suspend fun getAccounts(): Response<List<AccountModel>>

    @GET("service.php")//
    suspend fun getAccount(@Query("id") id: Int): Response<AccountModel>

    @POST("service.php")
    suspend fun addAccount(@Body account: AccountModel): Response<JsonObject>

    @PUT("service.php")
    suspend fun updateAccount(@Query("id") id: Int, @Body account: AccountModel): Response<JsonObject>

    @DELETE("service.php")
    suspend fun deleteAccount(@Query("id") id: Int): Response<JsonObject>
}

