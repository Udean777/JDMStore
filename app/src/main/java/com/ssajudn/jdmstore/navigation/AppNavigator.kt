package com.ssajudn.jdmstore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssajudn.jdmstore.ui.screens.Home
import com.ssajudn.jdmstore.ui.screens.LoginScreen
import com.ssajudn.jdmstore.utils.isLoggedIn

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val isLoggedIn = isLoggedIn(context)

    NavHost(navController, startDestination = if (isLoggedIn) "home" else "login") {
        composable("login") {
            LoginScreen(
                navController = navController
            )
        }
        composable("home") {
            Home(
                navController = navController
            )
        }
    }
}