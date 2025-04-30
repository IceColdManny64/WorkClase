package com.example.clasetrabajo.data.network

import com.example.clasetrabajo.data.model.AccountModel
import com.example.clasetrabajo.data.model.UserModel
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field
import retrofit2.Response
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("login.php") //POST login
    suspend fun login(@Body user: UserModel): Response<JsonObject>

    @POST("register.php") //POST login create account
    suspend fun loginCreate(@Body user: UserModel): Response<JsonObject>

    @GET("service.php") //get all accounts
    suspend fun getAccounts(): Response<List<AccountModel>>

    @GET("service.php")//get one account
    suspend fun getAccount(@Query("id") id: Int): Response<AccountModel>

    @POST("service.php")//create account
    suspend fun addAccount(@Body account: AccountModel): Response<JsonObject>

    @PUT("service.php")
    fun updateAccount(@Body requestBody: RequestBody): Call<JsonObject>

    @DELETE("service.php")//delete account
    suspend fun deleteAccount(@Query("id") id: Int): Response<JsonObject>
}

