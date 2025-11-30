package beyondeyesight._0.domain.service

import beyondeyesight._0.domain.model.GatheringEntity
import beyondeyesight._0.domain.repository.GatheringRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class GatheringService(
    private val gatheringRepository: GatheringRepository,
) {

    fun open(
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
    ): GatheringEntity {
        val entity = GatheringEntity.open(
            applyType = applyType,
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

        return gatheringRepository.create(entity)
    }

    fun close(uuid: UUID) {
        // todo: 상태를 close로 바꾸는걸로.
        gatheringRepository.delete(uuid)
    }
}