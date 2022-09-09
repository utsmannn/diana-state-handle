package com.diana.appcore.repository

import com.diana.lib.core.event.StateEventManager
import com.diana.appcore.data.entity.User
import org.koin.dsl.module

interface UserRepository {
    val userStateEventManager: StateEventManager<List<User>>

    suspend fun getUsers(page: Int = 1)

    companion object {
        fun inject() = module {
            single<UserRepository> { UserRepositoryImpl(get()) }
        }
    }
}