package beyondeyesight._0.domain.service

import beyondeyesight._0.domain.model.GatheringEntity
import beyondeyesight._0.domain.repository.GatheringRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class GatheringService(
    private val gatheringRepository: GatheringRepository,
    private val participantService: ParticipantService,
) {

    fun open(
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

        val gathering = gatheringRepository.create(entity)

        participantService.join(
            gatheringUuid = gathering.uuid,
            userUuid = hostUuid,
            isHost = true
        )

        return gathering
    }

    fun close(uuid: UUID) {
        val gathering = gatheringRepository.findByUuid(uuid)
            ?: throw IllegalArgumentException("Gathering not found with uuid: $uuid")
        gathering.close()
        gatheringRepository.save(gathering)
    }
}