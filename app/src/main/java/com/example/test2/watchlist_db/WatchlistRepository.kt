package com.example.test2.watchlist_db

import androidx.lifecycle.LiveData

class WatchlistRepository(private val watchlistDao: WatchlistDao) {
    val allWatchlist: LiveData<List<Watchlist>> = watchlistDao.getAllWatchlist()

    suspend fun addWatchlist(watchlist: Watchlist) {
        watchlistDao.addWatchlist(watchlist)
    }

    suspend fun updateWatchlist(watchlist: Watchlist) {
        watchlistDao.updateWatchlist(watchlist)
    }

    suspend fun deleteWatchlist(id: Int) {
        watchlistDao.deleteWatchlist(id)
    }

    suspend fun getTotalWatchlist(): Double {
        return watchlistDao.getTotalWatchlist() ?: 0.0
    }

    suspend fun getMostExpensiveWatchlist() : Double{
        return watchlistDao.getMostExpensiveWatchlist() ?: 0.0
    }
}