package com.diana.appcore.repository

import com.diana.lib.core.event.StateEventManager
import com.diana.lib.core.utils.default
import com.diana.appcore.data.entity.User
import com.diana.appcore.sources.NetworkSources

class UserRepositoryImpl(
    private val networkSources: NetworkSources
) : UserRepository {

    private val _userStateEventManager = default<List<User>>()

    override val userStateEventManager: StateEventManager<List<User>>
        get() = _userStateEventManager

    override suspend fun getUsers(page: Int) {
        networkSources.getList(page)
            .collect(_userStateEventManager)
    }

}