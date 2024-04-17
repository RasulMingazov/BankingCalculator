package com.psychojean.core

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class ComponentViewModel: InstanceKeeper.Instance, CoroutineScope {

    override val coroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate

    override fun onDestroy() {
        coroutineContext.cancel()
    }

}