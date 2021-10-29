package com.track.common.base

import kotlinx.coroutines.CoroutineDispatcher

class AppDispatchers (
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher
)