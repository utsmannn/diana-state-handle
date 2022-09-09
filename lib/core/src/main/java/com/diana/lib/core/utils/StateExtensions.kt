package com.diana.lib.core.utils

import com.diana.lib.core.event.MutableStateEventManager
import com.diana.lib.core.event.StateEvent
import com.diana.lib.core.event.StateEventFlow
import kotlinx.coroutines.flow.map

fun <T> default() = MutableStateEventManager<T>()

fun <T, U>StateEvent<T>.mapState(mapper: (T) -> U): StateEvent<U> {
    return when (this) {
        is StateEvent.Idle -> StateEvent.Idle()
        is StateEvent.Loading -> StateEvent.Loading()
        is StateEvent.Failure -> StateEvent.Failure(this.exception)
        is StateEvent.Success -> StateEvent.Success(mapper.invoke(this.data))
    }
}

fun <T, U>StateEventFlow<T>.mapState(mapper: (T) -> U): StateEventFlow<U> {
    return this.map { it.mapState(mapper) }
}