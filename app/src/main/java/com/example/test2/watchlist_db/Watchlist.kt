package com.example.test2.watchlist_db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity
data class Watchlist (
    val description : String,
    val amount : Double,
    val date : Long,
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    ){
        fun getFormattedDate(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return dateFormat.format(Date(date))
        }
}