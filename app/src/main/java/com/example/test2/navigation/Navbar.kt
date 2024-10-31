package com.example.test2.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

val items = listOf(
    BotNavbar(
        title = "Home",
        icon = Icons.Rounded.Home,
        route = "home"
    ),
    BotNavbar(
        title = "Watchlist",
        icon = Icons.Rounded.List,
        route = "watchlist"
    ),
    BotNavbar(
        title = "Account",
        icon = Icons.Rounded.AccountCircle,
        route = "account"
    ),
)

@Composable
fun Navbar(navController: NavController) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = false,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                label = {
                    Text(text = item.title, color = MaterialTheme.colorScheme.onBackground)
                }
            )
        }
    }
}