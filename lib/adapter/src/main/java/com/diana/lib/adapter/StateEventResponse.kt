package com.diana.lib.adapter

import com.diana.lib.core.event.StateEventFlow

data class StateEventResponse<T>(
    private val eventFlow: StateEventFlow<T>
) {

    fun eventFlow() = eventFlow

    companion object {
        internal fun <T>create(stateEventFlow: StateEventFlow<T>): StateEventResponse<T> {
            return StateEventResponse(stateEventFlow)
        }
    }
}