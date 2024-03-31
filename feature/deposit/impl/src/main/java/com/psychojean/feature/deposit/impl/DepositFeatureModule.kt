package com.psychojean.feature.deposit.impl

import com.psychojean.feature.deposit.impl.presentation.DepositPresentationModule
import dagger.Module

@Module(includes = [DepositPresentationModule::class])
interface DepositFeatureModule
