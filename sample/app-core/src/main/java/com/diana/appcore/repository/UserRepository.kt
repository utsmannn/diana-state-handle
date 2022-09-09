package com.diana.appcore.repository

import androidx.lifecycle.LiveData
import com.diana.lib.core.event.StateEventManager
import com.diana.appcore.data.entity.User
import com.diana.lib.core.event.StateEvent
import org.koin.dsl.module

interface UserRepository {
    val userStateEventLiveData: LiveData<StateEvent<List<User>>>

    // or use state event manager
    val userStateEventManager: StateEventManager<List<User>>

    suspend fun getUserUseLiveData(page: Int = 1)
    suspend fun getUsersUseManager(page: Int = 1)

    companion object {
        fun inject() = module {
            single<UserRepository> { UserRepositoryImpl(get()) }
        }
    }
}