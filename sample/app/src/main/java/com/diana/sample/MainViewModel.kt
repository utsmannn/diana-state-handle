package com.diana.sample

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

    fun subscribeUserManager(subscriber: StateEventSubscriber<List<User>>) {
        createEventToSubscriber(userManager, subscriber)
    }

    val usersLiveData get() = userRepository.userStateEventLiveData

    fun getUsersWithManager(page: Int) = userScope.launch {
        userRepository.getUsersUseManager(page)
    }

    fun getUsersWithLiveData(page: Int) = viewModelScope.launch {
        userRepository.getUserUseLiveData(page)
    }

    companion object {
        fun inject() = module {
            viewModel { MainViewModel(get()) }
        }
    }
}