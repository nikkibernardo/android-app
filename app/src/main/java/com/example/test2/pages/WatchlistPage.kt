package com.example.test2.pages

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.test2.AuthState
import com.example.test2.AuthViewModel
import com.example.test2.R
import com.example.test2.navigation.Navbar
import com.example.test2.watchlist_db.Watchlist
import com.example.test2.watchlist_db.WatchlistInputDialog
import com.example.test2.watchlist_db.WatchlistViewModel
import kotlinx.coroutines.launch

@Composable
fun WatchlistPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    watchlistViewModel : WatchlistViewModel = viewModel()
) {

    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    val watchlists by watchlistViewModel.allWatchlist.observeAsState(emptyList())
    val editableWatchlist = watchlistViewModel.editableWatchlist.collectAsState().value
    var search by remember { mutableStateOf("") }
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
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        watchlistViewModel.setEditableWatchlist()
                        isDialogOpen.value = true
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "AddWatchlist",
                        tint = Color.White
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopBar(modifier, navController, authViewModel)
                    Spacer(modifier = Modifier.height(12.dp))

                    Column (
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                    ){
                        Text(
                            text = "Watchlist",
                            modifier = Modifier
                                .padding(top = 20.dp),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = search,
                            onValueChange = { search = it },
                            label = { Text("Search here.....")},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

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

    if (isDialogOpen.value) {
        WatchlistInputDialog(
            isDialogOpen = isDialogOpen,
            onSave = { watchlist ->
                if (editableWatchlist.id == null) {
                    watchlistViewModel.addWatchlist(watchlist)
                } else {
                    watchlistViewModel.editWatchlist(watchlist)
                }
            },
            watchlist = editableWatchlist.watchlist
        )
    }

}

@Composable
fun WatchlistItem(
    watchlist: Watchlist,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
            //.padding(start = 20.dp, end = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                //Text(text = watchlist.title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                Text(text = watchlist.description, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                Text(text = "P${watchlist.amount}", color = Color.Gray)
                Text(text = watchlist.getFormattedDate(), color = Color.Gray)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = "editWatchlist")
                }
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Rounded.Delete, contentDescription = "deleteWatchlist")
                }
            }
    }



    }
}

@Composable
fun NoWatchlistFound(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.film),
            contentDescription = "noWatchlistFound",
            modifier = Modifier.size(200.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "No Watchlist Found", fontSize = 14.sp)
    }
}