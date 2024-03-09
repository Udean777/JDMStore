package com.ssajudn.jdmstore.retrofit

data class LoginReqBody(
    val client_id: String?,
    val client_secret: String?,
    val grant_type: String,
    val username: String,
    val password: String,
    val scope: String
)

data class RefreshTokenReqBody(
    val client_id: String,
    val client_secret: String,
    val grant_type: String,
    val refresh_token: String,
    val scope: String
)
