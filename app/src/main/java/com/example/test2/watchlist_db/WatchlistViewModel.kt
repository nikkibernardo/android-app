package com.example.test2.watchlist_db

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.test2.WatchlistApplication
import com.example.test2.transactionClass.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class EditableWatchlist(
    val watchlist: Watchlist? = null,
    val id: Int? = null
)

class WatchlistViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WatchlistRepository
    val allWatchlist: LiveData<List<Watchlist>> // LiveData to observe expense data changes

    private val _editableWatchlist = MutableStateFlow(EditableWatchlist())
    val editableWatchlist: StateFlow<EditableWatchlist> get() = _editableWatchlist

    private val _totalWatchlist = MutableStateFlow(0.0)
    val totalWatchlist: StateFlow<Double> get() = _totalWatchlist

    private val _watchlistData = MutableStateFlow<List<Transaction>>(emptyList())
    val watchlistData: StateFlow<List<Transaction>> get() = _watchlistData

    private val _mostExpensiveWatchlist = MutableStateFlow<Double?>(null)
    val mostExpensiveWatchlist: StateFlow<Double?> get() = _mostExpensiveWatchlist

    init {
        val watchlistDao = WatchlistApplication.watchlistDatabase.getWatchlistDao()
        repository = WatchlistRepository(watchlistDao)
        allWatchlist = repository.allWatchlist // Initialize LiveData from repository

        // Fetch initial expense data asynchronously
        viewModelScope.launch {
            _totalWatchlist.value = repository.getTotalWatchlist()
            _mostExpensiveWatchlist.value = repository.getMostExpensiveWatchlist()
            repository.allWatchlist.observeForever { expenses ->
                expenses?.let {
                    _watchlistData.value = it.map {
                        Transaction(it.date, it.amount.toFloat(), "Watchlist")
                    }
                    Log.d("WatchViewModel", "Watchlist Data: ${_watchlistData.value}")
                }
            }
        }
    }

fun addWatchlist(watchlist: Watchlist) {
    viewModelScope.launch {
        repository.addWatchlist(watchlist)
        val watchlist = repository.allWatchlist.value
        if (watchlist != null) {
            _watchlistData.value = watchlist.map {
                Transaction(it.date, it.amount.toFloat(), "Watchlist")
            }
            Log.d("WatchViewModel", "Watchlist Data: ${_watchlistData.value}")
        } else {
            Log.d("WatchViewModel", "Watchlist Data is null")
        }
    }
}

fun editWatchlist(watchlist: Watchlist) {
    viewModelScope.launch {
        repository.updateWatchlist(watchlist)
        val watchlists = repository.allWatchlist.value
        if (watchlists != null) {
            _watchlistData.value = watchlists.map {
                Transaction(it.date, it.amount.toFloat(), "Watchlist")
            }
            Log.d("WatchViewModel", "Watch Data: ${_watchlistData.value}")
        } else {
            Log.d("WatchViewModel", "Watch Data is null")
        }
    }
}

fun deleteWatchlist(id: Int) {
    viewModelScope.launch {
        repository.deleteWatchlist(id)
        val watchlists = repository.allWatchlist.value
        if (watchlists != null) {
            _watchlistData.value = watchlists.map {
                Transaction(it.date, it.amount.toFloat(), "Watchlist")
            }
            Log.d("WatchlistViewModel", "Watchlist Data: ${_watchlistData.value}")
        } else {
            Log.d("WatchlistViewModel", "Watchlist Data is null")
        }
    }
}

fun setEditableWatchlist(watchlist: Watchlist? = null, id: Int? = null) {
    _editableWatchlist.value = EditableWatchlist(watchlist, id)
}
}