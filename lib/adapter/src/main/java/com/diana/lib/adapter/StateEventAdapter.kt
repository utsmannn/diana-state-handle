package com.diana.lib.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class StateEventAdapter<T>(
    private val type: Type
) : CallAdapter<T, Call<StateEventResponse<T>>> {
    override fun responseType(): Type {
        return type
    }

    override fun adapt(call: Call<T>): Call<StateEventResponse<T>> {
        return StateEventCall(call)
    }

}