package com.example.test2.pages

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Transaction
import com.example.test2.AuthState
import com.example.test2.AuthViewModel
import com.example.test2.navigation.Navbar
import com.example.test2.watchlist_db.Watchlist
import com.example.test2.watchlist_db.WatchlistViewModel
import kotlinx.coroutines.launch

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    val watchlistViewModel: WatchlistViewModel = viewModel()
    val watchlists by watchlistViewModel.allWatchlist.observeAsState(emptyList())
    val isDialogOpen = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            bottomBar = {
                Navbar(navController)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                val watchlistData by watchlistViewModel.watchlistData.collectAsState(initial = emptyList())
                val allTransactions = (watchlistData).sortedBy { it.date }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                ) {

                    TopBar(modifier, navController, authViewModel)

                    //HomeContents()
                    Column (
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                    ){
                        Text(
                            text = "Dashboard",
                            modifier = Modifier
                                .padding(top = 15.dp, bottom = 10.dp),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                            ),
                            modifier = Modifier
                                .size(width = 440.dp, height = 160.dp)
                            //.padding(start = 16.dp, end = 16.dp)
                        ) {

                            Text(
                                text = "Hello,",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 20.dp, top = 50.dp),
                            )
                            Text(
                                text = "User Username",
                                modifier = Modifier
                                    .padding(start = 20.dp),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = "What would you like to do today?",
                            modifier = Modifier
                                .padding(top = 20.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        /////////CARDS
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                ),
                                modifier = Modifier
                                    .size(width = 190.dp, height = 160.dp)
                                    .padding(end = 4.dp)
                                    .clickable {
                                        navController.navigate("watchlist")
                                    }
                            ) {
                                Text(
                                    text = "Create Watchlist",
                                    fontSize = 22.sp,
                                    modifier = Modifier
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                ),
                                modifier = Modifier
                                    .size(width = 190.dp, height = 160.dp)
                                    .padding(start = 4.dp)
                            ) {
                                Text(
                                    text = "Recommend a Movie",
                                    fontSize = 22.sp,
                                    modifier = Modifier
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        Text(
                            text = "Recent Watchlist",
                            modifier = Modifier
                                .padding(top = 20.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        if(watchlists.isEmpty()){
                            NoWatchlistFound()
                        } else{
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(watchlists) { watchlist ->
                                    WatchlistItem(
                                        watchlist = watchlist,
                                        onDelete = {
                                            coroutineScope.launch {
                                                watchlistViewModel.deleteWatchlist(watchlist.id)
                                            }
                                        },
                                        onEdit = {
                                            watchlistViewModel.setEditableWatchlist(watchlist, watchlist.id)
                                            isDialogOpen.value = true
                                        }
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "MovieMojo",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Box(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable {
                authViewModel.signout()
            }
            .padding(6.dp),
        ){
            Icon(
                imageVector =  Icons.Rounded.Menu/*Icons.AutoMirrored.Rounded.ExitToApp*/,
                contentDescription = "signout",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun HomeContents() {
    Column {
        Text(
            text = "Dashboard",
            modifier = Modifier
                .padding(start = 15.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .size(width = 440.dp, height = 160.dp)
                .padding(start = 16.dp, end = 16.dp)
        ) {

            Text(
                text = "Hello,",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 20.dp, top = 50.dp),
            )
            Text(
                text = "User Username",
                modifier = Modifier
                    .padding(start = 20.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "What would you like to do today?",
            modifier = Modifier
                .padding(start = 20.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(100.dp))

        /*Text(text = "Homepage", fontSize = 32.sp)
        TextButton(onClick = {
            authViewModel.signout()
        }) {
            Text(text = "Signout")
        }*/
    }
}