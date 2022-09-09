package com.diana.lib.adapter

import com.diana.lib.core.event.StateEvent
import kotlinx.coroutines.flow.flow
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StateEventCall<T>(
    private val delegate: Call<T>
) : Call<StateEventResponse<T>> {

    private fun call(callback: Callback<StateEventResponse<T>>) = delegate.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val flow = response.asFlowStateEvent()
            val stateResponse = StateEventResponse.create(flow)
            callback.onResponse(this@StateEventCall, Response.success(stateResponse))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val state = StateEvent.Failure<T>(t)
            val flow = flow<StateEvent<T>> { emit(state) }
            val stateResponse = StateEventResponse.create(flow)
            callback.onResponse(this@StateEventCall, Response.success(stateResponse))
        }
    })

    override fun clone(): Call<StateEventResponse<T>> {
        return StateEventCall(delegate)
    }

    override fun execute(): Response<StateEventResponse<T>> {
        throw IllegalStateException(UNSUPPORTED_EXECUTE)
    }

    override fun enqueue(callback: Callback<StateEventResponse<T>>) {
        return call(callback)
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }

    companion object {
        private const val UNSUPPORTED_EXECUTE = "StateEventCall doesn't support execute!"
    }

}