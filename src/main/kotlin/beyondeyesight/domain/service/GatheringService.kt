package beyondeyesight.domain.service

import beyondeyesight.domain.model.GatheringEntity
import beyondeyesight.domain.repository.GatheringRepository
import beyondeyesight.domain.repository.ParticipantRepository
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Service
class GatheringService(
    private val gatheringRepository: GatheringRepository,
    private val participantService: ParticipantService,
    private val participantRepository: ParticipantRepository,
    private val lockService: LockService,
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

    fun join(gatheringUuid: UUID, userUuid: UUID) {
        val token = lockService.lockWithRetry(
            resourceName = "gathering",
            resourceId = gatheringUuid.toString(),
            expire = Duration.ofSeconds(10),
            waitTimeout = Duration.ofSeconds(5),
            retryInterval = Duration.ofMillis(100)
        ) ?: throw IllegalStateException("Failed to acquire lock for gathering: $gatheringUuid")

        try {
            val gathering = gatheringRepository.findByUuid(gatheringUuid)
                ?: throw IllegalArgumentException("Gathering not found with uuid: $gatheringUuid")

            val currentCount = participantRepository.countByGatheringUuid(gatheringUuid)

            if (currentCount + 1 > gathering.maxCapacity) {
                throw IllegalStateException("Gathering is full. Current + 1: ${currentCount + 1} , Max: ${gathering.maxCapacity}")
            }

            participantService.join(
                gatheringUuid = gatheringUuid,
                userUuid = userUuid,
                isHost = false
            )
        } finally {
            lockService.unlock("gathering", gatheringUuid.toString(), token)
        }
    }
}