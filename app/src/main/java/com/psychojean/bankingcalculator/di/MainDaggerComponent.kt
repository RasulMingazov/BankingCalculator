package com.psychojean.bankingcalculator.di

import com.psychojean.bankingcalculator.App
import com.psychojean.bankingcalculator.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface MainDaggerComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): MainDaggerComponent
    }

    fun inject(app: App)

    fun inject(activity: MainActivity)

    val rootComponentFactory: DefaultRootComponent.Factory

}
