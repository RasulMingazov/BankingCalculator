package com.psychojean.bankingcalculator

import android.app.Application
import com.psychojean.bankingcalculator.di.DaggerMainDaggerComponent
import com.psychojean.bankingcalculator.di.MainDaggerComponent

class BankingApp : Application() {

    internal lateinit var mainDaggerComponent: MainDaggerComponent

    override fun onCreate() {
        super.onCreate()

        mainDaggerComponent = DaggerMainDaggerComponent
            .builder()
            .application(this)
            .build()

        mainDaggerComponent.inject(this)

    }
}
