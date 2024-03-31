package com.psychojean.bankingcalculator.di

import com.psychojean.bankingcalculator.BankingApp
import com.psychojean.bankingcalculator.MainActivity
import com.psychojean.root.impl.DefaultRootComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface MainDaggerComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: BankingApp): Builder

        fun build(): MainDaggerComponent
    }

    fun inject(app: BankingApp)

    fun inject(activity: MainActivity)

    val rootComponentFactory: DefaultRootComponent.Factory

}
