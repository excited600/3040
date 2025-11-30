package beyondeyesight.infra.repository

import beyondeyesight._0.domain.model.UserEntity
import beyondeyesight._0.domain.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserJpaRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository,
): UserRepository {
    override fun create(userEntity: UserEntity): UserEntity {
        return userJpaRepository.save(userEntity)
    }
}