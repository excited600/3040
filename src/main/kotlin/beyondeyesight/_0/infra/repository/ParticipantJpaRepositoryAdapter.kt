package beyondeyesight._0.infra.repository

import beyondeyesight._0.domain.model.ParticipantEntity
import beyondeyesight._0.domain.repository.ParticipantRepository
import org.springframework.stereotype.Repository

@Repository
class ParticipantJpaRepositoryAdapter(
    private val participantJpaRepository: ParticipantJpaRepository
) : ParticipantRepository {
    override fun save(participantEntity: ParticipantEntity): ParticipantEntity {
        return participantJpaRepository.save(participantEntity)
    }
}
