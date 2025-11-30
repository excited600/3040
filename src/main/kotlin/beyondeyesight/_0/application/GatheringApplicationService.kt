package beyondeyesight._0.application

import beyondeyesight._0.domain.model.GatheringEntity
import beyondeyesight._0.domain.service.GatheringService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class GatheringApplicationService(
    private val gatheringService: GatheringService,
) {
    @Transactional
    fun <R> open(
        hostUuid: UUID,
        applyType: GatheringEntity.ApplyType,
        minCapacity: Int,
        maxCapacity: Int,
        genderRatioEnabled: Boolean,
        minAge: Int,
        maxAge: Int,
        maxMaleCount: Int?,
        maxFemaleCount: Int?,
        currentMaleCount: Int?,
        currentFemaleCount: Int?,
        totalAttendees: Int,
        fee: Int,
        discountEnabled: Boolean,
        offline: Boolean,
        place: String,
        category: GatheringEntity.Category,
        subCategory: String,
        status: GatheringEntity.Status,
        imageUrl: String,
        title: String,
        introduction: String,
        clickCount: Int,
        startDateTime: LocalDateTime,
        mapper: (GatheringEntity) -> R
    ): R {
        val gatheringEntity = gatheringService.open(
            hostUuid,
            applyType,
            minCapacity,
            maxCapacity,
            genderRatioEnabled,
            minAge,
            maxAge,
            maxMaleCount,
            maxFemaleCount,
            currentMaleCount,
            currentFemaleCount,
            totalAttendees,
            fee,
            discountEnabled,
            offline,
            place,
            category,
            subCategory,
            status,
            imageUrl,
            title,
            introduction,
            clickCount,
            startDateTime,
        )

        return mapper.invoke(gatheringEntity)
    }

    @Transactional
    fun close(uuid: UUID) {
        gatheringService.close(uuid)
    }
}