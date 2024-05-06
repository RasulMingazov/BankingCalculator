package com.psychojean.feature.deposit.api.domain

import com.psychojean.core.Error
import com.psychojean.core.RootResult

interface CalculateDepositUseCase {

    suspend operator fun invoke(depositInput: DepositInput): RootResult<DepositOutput, Error>

}
