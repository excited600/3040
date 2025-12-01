package beyondeyesight.infra.repository

import beyondeyesight.domain.model.UserEntity
import beyondeyesight.domain.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserJpaRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository,
): UserRepository {
    override fun create(userEntity: UserEntity): UserEntity {
        return userJpaRepository.save(userEntity)
    }
}