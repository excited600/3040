package beyondeyesight.infra.repository

import beyondeyesight._0.domain.model.GatheringEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface GatheringJpaRepository: JpaRepository<GatheringEntity, UUID> {
}