package beyondeyesight._0.domain.repository

import beyondeyesight._0.domain.model.GatheringEntity
import java.util.UUID

interface GatheringRepository {
    fun create(gatheringEntity: GatheringEntity): GatheringEntity

    fun delete(gatheringEntity: GatheringEntity)

    fun delete(uuid: UUID)
}