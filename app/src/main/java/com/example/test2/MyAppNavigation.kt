package com.example.test2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test2.pages.AccountPage
import com.example.test2.pages.HomePage
import com.example.test2.pages.LandingPage
import com.example.test2.pages.LoginPage
import com.example.test2.pages.SignupPage
import com.example.test2.pages.WatchlistPage

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "landing", builder = {
        composable("landing"){
            LandingPage(modifier, navController, authViewModel)
        }
        composable("login"){
            LoginPage(modifier, navController, authViewModel)
        }
        composable("signup"){
            SignupPage(modifier, navController, authViewModel)
        }
        composable("home"){
            HomePage(modifier, navController, authViewModel)
        }
        composable("watchlist"){
            WatchlistPage(modifier, navController, authViewModel)
        }
        composable("account"){
            AccountPage(modifier, navController, authViewModel)
        }

    })
}