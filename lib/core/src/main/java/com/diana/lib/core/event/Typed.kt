package com.diana.lib.core.event

import kotlinx.coroutines.flow.Flow

typealias StateEventFlow<T> = Flow<StateEvent<T>>