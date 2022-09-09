package com.diana.lib.adapter

import com.diana.lib.core.event.StateEvent
import com.diana.lib.core.event.StateEventFlow
import com.diana.lib.core.exception.StateApiException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import retrofit2.Response

inline fun <T, U> Response<T>.asFlowStateEventMap(
    crossinline mapper: (T) -> U
): StateEventFlow<U> {
    return flow {
        emit(StateEvent.Loading())
        delay(2000)
        val emitData = try {
            val body = body()
            if (isSuccessful && body != null) {
                val dataMapper = mapper.invoke(body)
                StateEvent.Success(dataMapper)
            } else {
                val message = errorBody()?.string().orEmpty()
                val exception = StateApiException(message, code())
                StateEvent.Failure(exception)
            }
        } catch (e: Throwable) {
            StateEvent.Failure(e)
        }

        emit(emitData)
    }
}


fun <T> Response<T>.asFlowStateEvent(): StateEventFlow<T> {
    return flow {
        emit(StateEvent.Loading())
        delay(2000)
        val emitData = try {
            val body = body()
            if (isSuccessful && body != null) {
                StateEvent.Success<T>(body)
            } else {
                val message = errorBody()?.string().orEmpty()
                val exception = StateApiException(message, code())
                StateEvent.Failure(exception)
            }
        } catch (e: Throwable) {
            StateEvent.Failure<T>(e)
        }

        emit(emitData)
    }
}