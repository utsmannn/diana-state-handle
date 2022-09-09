package com.diana.sample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diana.lib.subscriber.StateEventSubscriber
import com.diana.lib.subscriber.createEventToSubscriber
import com.diana.appcore.data.entity.User
import com.diana.appcore.repository.UserRepository
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val userManager = userRepository.userStateEventManager

    private val userScope = userManager.createScope(viewModelScope)

    fun subscribeUser(subscriber: StateEventSubscriber<List<User>>) {
        createEventToSubscriber(userManager, subscriber)
    }

    fun getUsers(page: Int) = userScope.launch {
        userRepository.getUsers(page)
    }

    companion object {
        fun inject() = module {
            viewModel { MainViewModel(get()) }
        }
    }
}