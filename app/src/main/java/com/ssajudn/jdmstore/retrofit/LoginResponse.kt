package com.ssajudn.jdmstore.retrofit

data class LoginResponse(
    val token_type: String,
    val expires_in: Long,
    val access_token: String,
    val refresh_token: String
)
