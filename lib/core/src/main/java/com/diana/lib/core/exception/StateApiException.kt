package com.diana.lib.core.exception

class StateApiException(
    private val stringBody: String = "",
    private val code: Int = -1
) : Throwable(stringBody) {

    fun code(): Int {
        return code
    }
}