package com.example.composescreenshotofficial

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "screenA") {
        composable("screenA") { SampleScreen(navController = navController) }
        composable("screenB") {
            LoginScreen(onLogin = { email, password, remember ->
                fakeLogin(email, password, remember)
            }, navController = navController)
        }
    }
}