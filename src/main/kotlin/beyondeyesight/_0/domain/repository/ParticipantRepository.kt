package beyondeyesight._0.domain.repository

import beyondeyesight._0.domain.model.ParticipantEntity

interface ParticipantRepository {
    fun save(participantEntity: ParticipantEntity): ParticipantEntity
}
