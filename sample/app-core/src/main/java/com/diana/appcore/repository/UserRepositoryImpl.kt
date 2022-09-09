package com.diana.appcore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.diana.appcore.data.entity.User
import com.diana.appcore.sources.NetworkSources
import com.diana.lib.core.event.StateEvent
import com.diana.lib.core.event.StateEventManager
import com.diana.lib.core.utils.defaultManager

class UserRepositoryImpl(
    private val networkSources: NetworkSources
) : UserRepository {

    private val _userStateEventLiveData = MutableLiveData<StateEvent<List<User>>>(StateEvent.Idle())
    private val _userStateEventManager = defaultManager<List<User>>()

    override val userStateEventLiveData: LiveData<StateEvent<List<User>>>
        get() = _userStateEventLiveData

    override val userStateEventManager: StateEventManager<List<User>>
        get() = _userStateEventManager

    override suspend fun getUserUseLiveData(page: Int) {
        networkSources.getList(page)
            .collect {
                _userStateEventLiveData.postValue(it)
            }
    }

    override suspend fun getUsersUseManager(page: Int) {
        networkSources.getList(page)
            .collect(_userStateEventManager)
    }

}