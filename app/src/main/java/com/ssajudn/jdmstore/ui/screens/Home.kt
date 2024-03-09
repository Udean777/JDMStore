package com.ssajudn.jdmstore.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ssajudn.jdmstore.viewModel.AuthViewModel
import com.ssajudn.jdmstore.viewModel.AuthViewModelFactory

@Composable
fun Home(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(application))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello World!")
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                authViewModel.logout()
                navController.navigate("login") {
                    popUpTo("home") {inclusive = true}
                    launchSingleTop = true
                }
            },
        ) {
            Text(text = "Logout")
        }
    }
}