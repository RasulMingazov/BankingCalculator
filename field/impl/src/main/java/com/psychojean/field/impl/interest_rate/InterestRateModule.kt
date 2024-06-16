package com.psychojean.field.impl.interest_rate

import com.psychojean.field.api.interest_rate.InterestRateComponent
import com.psychojean.field.api.interest_rate.ConvertInterestInputUseCase
import dagger.Binds
import dagger.Module

@Module
internal interface InterestRateModule {

    @Binds
    fun bindInterestValidationUseCase(interestValidationUseCase: DefaultConvertInterestInputUseCase): ConvertInterestInputUseCase

    @Binds
    fun bindInterestRateFactory(interestRateComponentFactory: DefaultInterestRateComponent.Factory): InterestRateComponent.Factory

}
