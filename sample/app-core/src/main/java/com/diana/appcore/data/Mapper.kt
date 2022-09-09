package com.diana.appcore.data

import com.diana.appcore.data.entity.User
import com.diana.appcore.data.response.UserResponses

object Mapper {
    fun mapUserResponses(userResponses: UserResponses?): List<User> {
        val mapper: (UserResponses.Data?) -> User = {
            User(
                id = it?.id ?: 0,
                email = it?.email.orEmpty(),
                name = "${it?.firstName.orEmpty()} ${it?.lastName.orEmpty()}"
            )
        }

        return userResponses?.data?.map(mapper).orEmpty()
    }
}