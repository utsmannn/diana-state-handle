package com.diana.samplecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diana.appcore.repository.UserRepository
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val usersLiveData get() = userRepository.userStateEventLiveData

    fun getUsersWithLiveData(page: Int) = viewModelScope.launch {
        userRepository.getUserUseLiveData(page)
    }

    companion object {
        fun inject() = module {
            viewModel { MainViewModel(get()) }
        }
    }
}