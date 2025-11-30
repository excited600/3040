package beyondeyesight._0.infra.repository

import beyondeyesight._0.domain.model.GatheringEntity
import beyondeyesight._0.domain.repository.GatheringRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class GatheringJpaRepositoryAdapterI(
    private val gatheringJpaRepository: GatheringJpaRepository,
): GatheringRepository {
    override fun create(gatheringEntity: GatheringEntity): GatheringEntity {
        return gatheringJpaRepository.save(gatheringEntity)
    }

    override fun delete(gatheringEntity: GatheringEntity) {
        gatheringJpaRepository.delete(gatheringEntity)
    }

    override fun delete(uuid: UUID) {
        gatheringJpaRepository.deleteById(uuid)
    }
}