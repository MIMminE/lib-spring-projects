package lib_spring_data.study_redis.dto

import lib_spring_data.study_redis.model.User

/**
 *  실무적인 관점에서 하나의 DTO 파일에 여러개의 DTO 클래스를 두는 것은 상황에 따라 유동적으로 사용하는 것을 권장한다.
 *  프로젝트 규모가 크지 않는다면 관련있는 DTO 들을 하나의 파일로 관리하는 것도 괜찮으나
 *  프로젝트 규모가 크거나 협업이 많이 발생하는 프로젝트라면 각 DTO 별로 파일을 두는 것이 좋다.
 */

data class CreateUserRequest(
    val email: String,
    val name: String,
    val age: Int
)

data class UserResponse(
    val id: String,
    val email: String,
    val name: String,
    val age: Int,
    val createAt: String
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id!!,
                email = user.email,
                name = user.name,
                age = user.age,
                createAt = user.createdAt.toString()
            )
        }
    }
}
