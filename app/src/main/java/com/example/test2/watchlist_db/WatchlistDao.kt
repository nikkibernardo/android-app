package com.example.test2.watchlist_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM Watchlist")
    fun getAllWatchlist(): LiveData<List<Watchlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWatchlist(expense: Watchlist)

    @Update
    suspend fun updateWatchlist(expense: Watchlist)

    @Query("DELETE FROM Watchlist WHERE id = :id")
    suspend fun deleteWatchlist(id : Int)

    @Query("SELECT SUM(amount) from Watchlist")
    suspend fun getTotalWatchlist() : Double?

    @Query("SELECT MAX(amount) from Watchlist")
    suspend fun getMostExpensiveWatchlist() : Double?
}