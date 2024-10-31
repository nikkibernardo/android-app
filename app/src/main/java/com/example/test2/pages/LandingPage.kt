package com.example.test2.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.test2.R
import com.example.test2.AuthViewModel

@Composable
fun LandingPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val backgroundColor = MaterialTheme.colorScheme.primaryContainer
    val buttonColor = MaterialTheme.colorScheme.secondary
    val buttonTextColor = MaterialTheme.colorScheme.onSecondary
    val authState = authViewModel.authState.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.film),
                contentDescription = "A call icon for calling",
                modifier = Modifier.width(60.dp).height(60.dp).padding(start = 10.dp, bottom = 10.dp)
            )
            Text(
                text = "Welcome to",
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "MovieMojo",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "Discover thousands of movies, TV shows, and people. Explore now!",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(start = 10.dp, bottom = 200.dp)
            )

            Button(
                onClick = {
                    navController.navigate("login")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = buttonTextColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 190.dp, end = 16.dp)
                    .height(60.dp)
            ) {
                Text(text = "Get Started", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}