package com.example.test2

import android.content.Context
import androidx.room.Room
import com.example.test2.watchlist_db.WatchlistDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class WatchlistApplication {
    companion object {
        lateinit var watchlistDatabase: WatchlistDatabase
        private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        fun initialize(context: Context) {
            applicationScope.launch {
                watchlistDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    WatchlistDatabase::class.java,
                    WatchlistDatabase.NAME
                ).apply{
                    allowMainThreadQueries()
                }.build()
            }
        }
    }
}