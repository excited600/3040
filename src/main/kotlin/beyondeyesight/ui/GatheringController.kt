package beyondeyesight.ui

import beyondeyesight.application.GatheringApplicationService
import beyondeyesight.domain.model.GatheringEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.UUID

@RestController
@RequestMapping("/gatherings")
class GatheringController(
    private val gatheringApplicationService: GatheringApplicationService
) {

    @DeleteMapping("/{uuid}")
    fun close(@PathVariable uuid: UUID): ResponseEntity<Unit> {
        gatheringApplicationService.close(uuid)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{gatheringUuid}/join")
    fun join(
        @PathVariable gatheringUuid: UUID,
        @RequestBody request: JoinGatheringRequest
    ): ResponseEntity<Unit> {
        gatheringApplicationService.join(gatheringUuid, request.userUuid)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/")
    fun open(@RequestBody request: OpenGatheringRequest): ResponseEntity<OpenGatheringResponse> {
        return gatheringApplicationService.open(
            hostUuid = request.hostUuid,
            applyType = request.applyType,
            minCapacity = request.minCapacity,
            maxCapacity = request.maxCapacity,
            genderRatioEnabled = request.genderRatioEnabled,
            minAge = request.minAge,
            maxAge = request.maxAge,
            maxMaleCount = request.maxMaleCount,
            maxFemaleCount = request.maxFemaleCount,
            currentMaleCount = request.currentMaleCount,
            currentFemaleCount = request.currentFemaleCount,
            totalAttendees = request.totalAttendees,
            fee = request.fee,
            discountEnabled = request.discountEnabled,
            offline = request.offline,
            place = request.place,
            category = request.category,
            subCategory = request.subCategory,
            status = request.status,
            imageUrl = request.imageUrl,
            title = request.title,
            introduction = request.introduction,
            clickCount = request.clickCount,
            startDateTime = request.startDateTime,
            mapper = {gatheringEntity -> ResponseEntity.ok(OpenGatheringResponse.from(gatheringEntity))}
        )

    }

    class OpenGatheringRequest(
        val hostUuid: UUID,
        val applyType: GatheringEntity.ApplyType,
        val minCapacity: Int,
        val maxCapacity: Int,
        val genderRatioEnabled: Boolean,
        val minAge: Int,
        val maxAge: Int,
        val maxMaleCount: Int?,
        val maxFemaleCount: Int?,
        val currentMaleCount: Int?,
        val currentFemaleCount: Int?,
        val totalAttendees: Int,
        val fee: Int,
        val discountEnabled: Boolean,
        val offline: Boolean,
        val place: String,
        val category: GatheringEntity.Category,
        val subCategory: String,
        val status: GatheringEntity.Status, // 이거 안받게
        val imageUrl: String,
        val title: String,
        val introduction: String,
        val clickCount: Int, // 이거 안받게
        val startDateTime: LocalDateTime,
    )

    class OpenGatheringResponse(
        val uuid: UUID,
        val applyType: GatheringEntity.ApplyType,
        val minCapacity: Int,
        val maxCapacity: Int,
        val genderRatioEnabled: Boolean,
        val minAge: Int,
        val maxAge: Int,
        val maxMaleCount: Int?,
        val maxFemaleCount: Int?,
        val currentMaleCount: Int?,
        val currentFemaleCount: Int?,
        val totalAttendees: Int,
        val fee: Int,
        val discountEnabled: Boolean,
        val offline: Boolean,
        val place: String,
        val category: GatheringEntity.Category,
        val subCategory: String,
        val status: GatheringEntity.Status,
        val imageUrl: String,
        val title: String,
        val introduction: String,
        val clickCount: Int,
        val startDateTime: LocalDateTime,
    ) {
        companion object {
            fun from(gatheringEntity: GatheringEntity): OpenGatheringResponse {
                return OpenGatheringResponse(
                    uuid = gatheringEntity.uuid,
                    applyType = gatheringEntity.applyType,
                    minCapacity = gatheringEntity.minCapacity,
                    maxCapacity = gatheringEntity.maxCapacity,
                    genderRatioEnabled = gatheringEntity.genderRatioEnabled,
                    minAge = gatheringEntity.minAge,
                    maxAge = gatheringEntity.maxAge,
                    maxMaleCount = gatheringEntity.maxMaleCount,
                    maxFemaleCount = gatheringEntity.maxFemaleCount,
                    currentMaleCount = gatheringEntity.currentMaleCount,
                    currentFemaleCount = gatheringEntity.currentFemaleCount,
                    totalAttendees = gatheringEntity.totalAttendees,
                    fee = gatheringEntity.fee,
                    discountEnabled = gatheringEntity.discountEnabled,
                    offline = gatheringEntity.offline,
                    place = gatheringEntity.place,
                    category = gatheringEntity.category,
                    subCategory = gatheringEntity.subCategory,
                    status = gatheringEntity.status,
                    imageUrl = gatheringEntity.imageUrl,
                    title = gatheringEntity.title,
                    introduction = gatheringEntity.introduction,
                    clickCount = gatheringEntity.clickCount,
                    startDateTime = gatheringEntity.startDateTime,
                )
            }
        }
    }

    class JoinGatheringRequest(
        val userUuid: UUID
    )
}

