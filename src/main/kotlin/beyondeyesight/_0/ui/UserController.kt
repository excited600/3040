package beyondeyesight._0.ui

import beyondeyesight._0.application.UserApplicationService
import beyondeyesight._0.domain.model.UserEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(("/users"))
class UserController(
    private val userApplicationService: UserApplicationService,
) {

    @PostMapping("/")
    fun signUp(@RequestBody request: SignUpRequest): SignUpResponse {
        return userApplicationService.signUp(
            email = request.email,
            nickname = request.nickname,
            age = request.age,
            gender = request.gender,
            introduction = request.introduction,
            password = request.password,
            phoneNumber = request.phoneNumber,
            phoneAuthenticated = request.phoneAuthenticated,
            mapper = {userEntity: UserEntity -> SignUpResponse.from(userEntity)}
        )
    }

    class SignUpRequest(
        var email: String,
        var nickname: String,
        var age: Int,
        var gender: UserEntity.Gender,
        var introduction: String,
        var password: String,
        var phoneNumber: String,
        var phoneAuthenticated: Boolean,
    )

    class SignUpResponse(
        var uuid: UUID,
        var email: String,
        var nickname: String,
        var age: Int,
        var gender: UserEntity.Gender,
        var introduction: String,
        var phoneNumber: String,
        var phoneAuthenticated: Boolean,
        var hearts: Int,
        var isPrivate: Boolean,
        var provider: UserEntity.Provider
    ) {
        companion object {
            fun from(userEntity: UserEntity): SignUpResponse {
                return SignUpResponse(
                    uuid = userEntity.uuid,
                    email = userEntity.email,
                    nickname = userEntity.nickname,
                    age = userEntity.age,
                    gender = userEntity.gender,
                    introduction = userEntity.introduction,
                    phoneNumber = userEntity.phoneNumber,
                    phoneAuthenticated = userEntity.phoneAuthenticated,
                    hearts = userEntity.hearts,
                    isPrivate = userEntity.isPrivate,
                    provider = userEntity.provider
                )
            }
        }
    }
}