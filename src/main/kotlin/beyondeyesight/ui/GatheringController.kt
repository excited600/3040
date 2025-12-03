package beyondeyesight.ui

import beyondeyesight.application.GatheringApplicationService
import beyondeyesight.domain.model.GatheringEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @GetMapping("/page")
    fun getPage(
        @RequestParam(required = false) cursor: LocalDateTime?,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<GetGatheringsResponse> {
        return gatheringApplicationService.getPage(cursor, size) { gatherings, hasNext, nextCursor ->
            ResponseEntity.ok(GetGatheringsResponse.from(gatherings, hasNext, nextCursor))
        }
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
            fee = request.fee,
            discountEnabled = request.discountEnabled,
            offline = request.offline,
            place = request.place,
            category = request.category,
            subCategory = request.subCategory,
            imageUrl = request.imageUrl,
            title = request.title,
            introduction = request.introduction,
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
        val fee: Int,
        val discountEnabled: Boolean,
        val offline: Boolean,
        val place: String,
        val category: GatheringEntity.Category,
        val subCategory: String, // 수정
        val imageUrl: String,
        val title: String,
        val introduction: String,
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

    data class GatheringDto(
        val uuid: UUID,
        val title: String,
        val introduction: String,
        val category: GatheringEntity.Category,
        val subCategory: String,
        val place: String,
        val fee: Int,
        val maxCapacity: Int,
        val totalAttendees: Int,
        val status: GatheringEntity.Status,
        val startDateTime: LocalDateTime,
        val imageUrl: String,
        val createdAt: LocalDateTime
    ) {
        companion object {
            fun from(entity: GatheringEntity): GatheringDto {
                return GatheringDto(
                    uuid = entity.uuid,
                    title = entity.title,
                    introduction = entity.introduction,
                    category = entity.category,
                    subCategory = entity.subCategory,
                    place = entity.place,
                    fee = entity.fee,
                    maxCapacity = entity.maxCapacity,
                    totalAttendees = entity.totalAttendees,
                    status = entity.status,
                    startDateTime = entity.startDateTime,
                    imageUrl = entity.imageUrl,
                    createdAt = entity.createdAt
                )
            }
        }
    }

    data class GetGatheringsResponse(
        val gatherings: List<GatheringDto>,
        val hasNext: Boolean,
        val nextCursor: LocalDateTime?
    ) {
        companion object {
            fun from(entities: List<GatheringEntity>, hasNext: Boolean, nextCursor: LocalDateTime?): GetGatheringsResponse {
                return GetGatheringsResponse(
                    gatherings = entities.map { GatheringDto.from(it) },
                    hasNext = hasNext,
                    nextCursor = nextCursor
                )
            }
        }
    }
}

