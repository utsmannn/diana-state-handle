package com.diana.lib.adapter

import android.util.Log
import com.diana.lib.core.event.StateEventFlow
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class StateEventAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)

        if (getRawType(responseType) != StateEventResponse::class.java) {
            Log.e(TAG, UNSUPPORTED_ERROR)
        }

        check(responseType is ParameterizedType) { "Response must be parameterized as NetworkResponse<Foo> or NetworkResponse<out Foo>" }

        val successBodyType = getParameterUpperBound(0, responseType)

        return StateEventAdapter<StateEventFlow<*>>(successBodyType)
    }

    companion object {
        private const val TAG = "StateEvent Adapter"
        private const val UNSUPPORTED_ERROR = "return type must be as StateEventResponse<Foo>!"
        fun create() = StateEventAdapterFactory()
    }
}