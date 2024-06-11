package com.psychojean.field.impl.interest_rate

import com.psychojean.field.api.interest_rate.InterestRateComponent
import com.psychojean.field.api.interest_rate.InterestValidationUseCase
import dagger.Binds
import dagger.Module

@Module
internal interface InterestRateModule {

    @Binds
    fun bindInterestValidationUseCase(interestValidationUseCase: DefaultInterestValidationUseCase): InterestValidationUseCase

    @Binds
    fun bindInterestRateFactory(interestRateComponentFactory: DefaultInterestRateComponent.Factory): InterestRateComponent.Factory

}
