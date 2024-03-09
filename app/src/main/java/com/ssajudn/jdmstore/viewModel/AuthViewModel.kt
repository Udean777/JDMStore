package com.ssajudn.jdmstore.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssajudn.jdmstore.retrofit.LoginReqBody
import com.ssajudn.jdmstore.retrofit.RefreshTokenReqBody
import com.ssajudn.jdmstore.retrofit.RetrofitInstance
import com.ssajudn.jdmstore.retrofit.RetrofitService
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(private val application: Application): ViewModel(){
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences("AuthPreferences", Context.MODE_PRIVATE)

    private fun saveToken(accessToken: String?, expiresIn: Long, refreshToken: String?){
        val editor = sharedPreferences.edit()
        editor.putString("access_token", accessToken)
        editor.putLong("expires_in", expiresIn)
        editor.putString("refresh_token", refreshToken)
        editor.apply()
    }

    fun login(username: String, password: String, onLoginSuccess: (String) -> Unit) {
        viewModelScope.launch {
            val requestBody = LoginReqBody(
                RetrofitService.client_id,
                RetrofitService.client_secret,
                grant_type = "password",
                username = username,
                password = password,
                scope = "*"
            )

            val response = withContext(Dispatchers.IO){
                RetrofitInstance.api.login(requestBody).execute()
            }

            if (response.isSuccessful) {
                val newToken = response.body()?.access_token
                val newExpiresIn = response.body()?.expires_in ?: 0
                val refreshToken = response.body()?.refresh_token
                saveToken(newToken, newExpiresIn, refreshToken)

                if (newToken != null) {
                    onLoginSuccess(newToken)
                }
                Toast.makeText(application, "Login berhasil", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(application, "Login gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logout() {
        val sharedPreferences = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("access_token")
        editor.remove("refresh_token")
        editor.apply()
    }

    private fun getToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    private fun getExpiresIn(): Long {
        return sharedPreferences.getLong("expires_in", 0)
    }

    private fun getRefreshToken(): String? {
        return sharedPreferences.getString("refresh_token", null)
    }

    fun checkRefreshToken(){
        viewModelScope.launch {
            val currentTime = System.currentTimeMillis() / 1000
            val expiresIn = getExpiresIn()

            if(currentTime >= expiresIn){
                refreshToken()
            }
        }
    }

    private suspend fun refreshToken() {
        val refreshToken = getRefreshToken() ?: return
        val reqBody = RefreshTokenReqBody(
            RetrofitService.client_id,
            RetrofitService.client_secret,
            grant_type = "refresh_token",
            refresh_token = refreshToken,
            scope = "*"
        )

        val res = withContext(Dispatchers.IO){
            RetrofitInstance.api.refreshToken(reqBody).execute()
        }

        if (res.isSuccessful){
            val newToken= res.body()?.access_token
            val newExpiresIn =  res.body()?.expires_in ?: 0

            saveToken(newToken, newExpiresIn, refreshToken)
        }else{
            Log.d("access token", "Error in authviewmodel")
        }
    }
}