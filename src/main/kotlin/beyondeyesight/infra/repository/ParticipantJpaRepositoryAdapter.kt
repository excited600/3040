package beyondeyesight.infra.repository

import beyondeyesight._0.domain.model.ParticipantEntity
import beyondeyesight._0.domain.repository.ParticipantRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ParticipantJpaRepositoryAdapter(
    private val participantJpaRepository: ParticipantJpaRepository
) : ParticipantRepository {
    override fun save(participantEntity: ParticipantEntity): ParticipantEntity {
        return participantJpaRepository.save(participantEntity)
    }

    override fun countByGatheringUuid(gatheringUuid: UUID): Long {
        return participantJpaRepository.countByGatheringUuid(gatheringUuid)
    }
}
