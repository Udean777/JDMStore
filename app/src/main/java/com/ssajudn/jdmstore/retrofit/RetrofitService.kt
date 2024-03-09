package com.ssajudn.jdmstore.retrofit

import io.github.cdimascio.dotenv.dotenv
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitService {

    @POST("oauth/token")
    @Headers("Content-Type: application/json")
    fun login(@Body requestBody: LoginReqBody): Call<LoginResponse>

    @POST("oauth/token")
    @Headers("Content-Type: application/json")
    fun refreshToken(@Body requestBody: RefreshTokenReqBody): Call<LoginResponse>

    companion object {
        private val dotenv = dotenv {
            directory = "/assets"
            filename = "env"
        }

        val BASE_URL: String = dotenv["API_URL"]
        val client_id: String = dotenv["client_id"]
        val client_secret: String = dotenv["client_secret"]
    }
}