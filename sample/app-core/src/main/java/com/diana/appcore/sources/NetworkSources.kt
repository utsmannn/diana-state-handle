package com.diana.appcore.sources

import com.diana.lib.core.event.StateEventFlow
import com.diana.lib.core.utils.mapState
import com.diana.appcore.data.Mapper
import com.diana.appcore.data.entity.User
import org.koin.dsl.module

class NetworkSources(private val webServices: WebServices) {

    suspend fun getList(page: Int): StateEventFlow<List<User>> {
        return webServices.getList(page).eventFlow().mapState {
            Mapper.mapUserResponses(it)
        }
    }

    companion object {
        fun inject() = module {
            single { NetworkSources(get()) }
        }
    }
}