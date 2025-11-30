package beyondeyesight._0.infra.repository

import beyondeyesight._0.domain.model.ParticipantEntity
import beyondeyesight._0.domain.model.ParticipantId
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantJpaRepository : JpaRepository<ParticipantEntity, ParticipantId> {
}
