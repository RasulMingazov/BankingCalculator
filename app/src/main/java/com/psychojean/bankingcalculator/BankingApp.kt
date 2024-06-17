package com.psychojean.bankingcalculator

import android.app.Application
import com.psychojean.bankingcalculator.di.AppContainer
import com.psychojean.bankingcalculator.di.DefaultAppContainer

class BankingApp : Application() {

    internal lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer()
    }
}
