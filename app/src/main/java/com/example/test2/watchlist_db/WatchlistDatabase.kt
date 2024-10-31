package com.example.test2.watchlist_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Watchlist::class], version = 1)
abstract class WatchlistDatabase: RoomDatabase() {
    companion object{
        const val NAME = "Watchlist_DB"
    }

    abstract fun getWatchlistDao() : WatchlistDao
}
