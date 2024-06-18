package com.psychojean.root.api.di

import com.psychojean.root.api.RootComponent

interface RootContainer {

    val rootComponentFactory: RootComponent.Factory

}