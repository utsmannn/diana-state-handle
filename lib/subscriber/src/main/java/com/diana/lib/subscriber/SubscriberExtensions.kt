package com.diana.lib.subscriber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diana.lib.core.event.StateEventManager

fun <T> ViewModel.createEventToSubscriber(
    eventManager: StateEventManager<T>,
    subscriber: StateEventSubscriber<T>
) {
    eventManager.subscribe(
        scope = viewModelScope,
        onIdle = subscriber::onIdle,
        onLoading = subscriber::onLoading,
        onFailure = subscriber::onFailure,
        onSuccess = subscriber::onSuccess
    )
}