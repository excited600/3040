package beyondeyesight._0.domain.repository

import beyondeyesight._0.domain.model.UserEntity

interface UserRepository {
    fun create(userEntity: UserEntity): UserEntity
}