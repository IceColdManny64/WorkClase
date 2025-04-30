package com.example.clasetrabajo.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clasetrabajo.data.model.AccountModel
import com.example.clasetrabajo.data.network.RetrofitClient
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class AccountViewModel: ViewModel() {
    val api = RetrofitClient.api

    fun getAccounts(onResult: (Response<List<AccountModel>>) -> Unit){
        viewModelScope.launch{
            try {
                val response = api.getAccounts()
                Log.d("debug", response.toString())
                onResult(response)
            } catch (exception: Exception){
                Log.d("debug", "API ERROR: $exception")
            }
        }
    }

    fun getAccount(id: Int, onResult:(Response<AccountModel>) -> Unit ) {
        viewModelScope.launch {

            try {
                val response = api.getAccount(id)
                Log.d("debug", response.toString())
                onResult(response)
            } catch (exception: Exception) {
                Log.d("debug", "API ERROR: $exception")
            }
        }
    }
    //service is received in backend
    fun createAccount(service: AccountModel, onResult: (Response:JsonObject?) -> Unit){
        viewModelScope.launch{
            try {
                val response = api.addAccount(service)
                if(response.isSuccessful){
                    val jsonResponse = response.body()
                    Log.d("debug", "$jsonResponse" /* or jsonResponse.toString()*/)
                    onResult(jsonResponse)
                }else{
                    Log.d("debug", "ERROR: ${response.errorBody()?.string()}")
                    onResult(null)
                }
            }catch (exception: Exception){
                Log.d("debug", "API CALL FAILED: $exception")
                onResult(null)
            }
        }
    }
    fun updateAccount(id: Int, account: AccountModel, onResult: (JsonObject?) -> Unit) {
        val json = JsonObject().apply {
            addProperty("id", id)
            addProperty("name", account.name)
            addProperty("username", account.username)
            addProperty("password", account.password)
            addProperty("description", account.description)
            addProperty("imageURL", account.imageURL)
        }

        val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

        api.updateAccount(requestBody).enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: retrofit2.Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val jsonResponse = response.body()
                    Log.d("debug", "MSG: $jsonResponse")
                    onResult(jsonResponse)
                } else {
                    Log.d("debug", "ERROR: ${response.errorBody()?.string()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: retrofit2.Call<JsonObject>, t: Throwable) {
                Log.d("debug", "API CALL FAILED: $t")
                onResult(null)
            }
        })
    }



    fun deleteAccount(id: Int, onResult:(JsonObject?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = api.deleteAccount(id)
                if (response.isSuccessful) {
                    val jsonResponse = response.body()
                    Log.d("debug", "MSG: ${response.body()}")
                    onResult(jsonResponse)
                } else {
                    Log.d("debug", "ERROR: ${response.body()}")
                    onResult(null)
                }
            } catch (exception: Exception) {
                Log.d("debug", "API CALL FAILED: $exception")
                onResult(null)
            }
        }
    }
}

