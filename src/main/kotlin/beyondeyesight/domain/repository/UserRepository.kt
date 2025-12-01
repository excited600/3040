package beyondeyesight.domain.repository

import beyondeyesight.domain.model.UserEntity

interface UserRepository {
    fun create(userEntity: UserEntity): UserEntity
}