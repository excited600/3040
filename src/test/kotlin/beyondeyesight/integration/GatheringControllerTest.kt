package beyondeyesight.integration

import beyondeyesight.domain.model.GatheringEntity
import beyondeyesight.domain.model.UserEntity
import beyondeyesight.domain.repository.UserRepository
import beyondeyesight.ui.GatheringController
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

class GatheringControllerTest: IntegrationTestBase() {

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

        val request = GatheringController.OpenGatheringRequest(
            hostUuid = host.uuid,
            applyType = GatheringEntity.ApplyType.FIRST_IN,
            minCapacity = 2,
            maxCapacity = 5,
            genderRatioEnabled = false,
            minAge = 20,
            maxAge = 30,
            maxMaleCount = null,
            maxFemaleCount = null,
            fee = 10000,
            discountEnabled = false,
            offline = true,
            place = "Seoul",
            category = GatheringEntity.Category.ACTIVITY,
            subCategory = "FOOTBALL",
            imageUrl = "http://example.com/image.jpg",
            title = "Football Gathering",
            introduction = "Let's play football together!",
            startDateTime = java.time.LocalDateTime.now().plusDays(7)
        )

        webTestClient.post()
            .uri("/gatherings/")
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(GatheringController.OpenGatheringResponse::class.java)


    }
}