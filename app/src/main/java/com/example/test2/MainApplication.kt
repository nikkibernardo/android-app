package com.example.test2

import android.app.Application

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        WatchlistApplication.initialize(this)
    }
}