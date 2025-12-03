package beyondeyesight.integration

import beyondeyesight.domain.model.GatheringEntity
import beyondeyesight.domain.model.UserEntity
import beyondeyesight.domain.repository.UserRepository
import beyondeyesight.ui.GatheringController
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

class GatheringControllerTest: EndToEndTestBase() {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `Gathering Open`() {
        val host = userRepository.save(
            UserEntity.signUp(
                email = "email",
                nickname = "nickname",
                age = 25,
                gender = UserEntity.Gender.M,
                introduction = "intro",
                password = "password",
                phoneNumber = "01012345671",
                phoneAuthenticated = true,
            )
        )
        val anyInt = 1
        val anyString = "string"

        val request = GatheringController.OpenGatheringRequest(
            hostUuid = host.uuid,
            applyType = GatheringEntity.ApplyType.FIRST_IN,
            minCapacity = anyInt,
            maxCapacity = anyInt + 1,
            genderRatioEnabled = false,
            minAge = anyInt,
            maxAge = anyInt + 1,
            maxMaleCount = null,
            maxFemaleCount = null,
            fee = anyInt,
            discountEnabled = false,
            offline = true,
            place = anyString,
            category = GatheringEntity.Category.ACTIVITY,
            subCategory = anyString,
            imageUrl = anyString,
            title = anyString,
            introduction = anyString,
            startDateTime = java.time.LocalDateTime.now().plusDays(7)
        )

        webTestClient.post()
            .uri("/gatherings/")
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(GatheringController.OpenGatheringResponse::class.java)

        val count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM gatherings",
            Long::class.java
        )

        assertThat(count).isEqualTo(1L)

//        객체로 변환하는 예제 코드
//        val map = jdbcTemplate.queryForMap(
//            "SELECT COUNT(*) FROM gatherings",
//        )
//        val gathering = objectMapper.convertValue(map, GatheringEntity::class.java)



    }
}