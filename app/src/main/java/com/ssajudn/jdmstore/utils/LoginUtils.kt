package com.ssajudn.jdmstore.utils

import android.content.Context

fun saveLoginState(context: Context, accessToken: String) {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("access_token", accessToken)
    editor.apply()
}

fun isLoggedIn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.contains("access_token")
}

