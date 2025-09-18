package lib_spring_data.study_redis.redis_publish.service

import lib_spring_data.study_redis.redis_publish.dto.CreateUserRequest
import lib_spring_data.study_redis.redis_publish.dto.UserResponse
import lib_spring_data.study_redis.redis_publish.model.User
import lib_spring_data.study_redis.redis_publish.repository.CustomUserRepository
import lib_spring_data.study_redis.redis_publish.repository.UserRedisRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRedisRepository: UserRedisRepository,
    private val customUserRepository: CustomUserRepository
) {
    // CRUD 기본 작업
    fun createUser(request: CreateUserRequest): UserResponse {
        val user = User(
            email = request.email,
            name = request.name,
            age = request.age
        )
        val savedUser = userRedisRepository.save(user)
        return UserResponse.from(savedUser)
    }

    fun findById(id: String): UserResponse? {
        return userRedisRepository.findById(id)
            .map { UserResponse.from(it) }
            .orElse(null)
    }

    fun findByEmail(email: String): UserResponse? {
        return userRedisRepository.findByEmail(email)?.let { UserResponse.from(it) }
    }

    fun findAllUser(): List<UserResponse> {
        return userRedisRepository.findAll().map { UserResponse.from(it) }
    }

    fun deleteUser(id: String) {
        userRedisRepository.deleteById(id)
    }

    // 고급 기능
    fun cacheUserSession(userId: String, sessionData: Map<String, Any>){
        customUserRepository.saveUserData("session:$userId", sessionData)
    }
    fun getUserSession(userId: String): Map<String, Any> {
        return customUserRepository.getUserData("session:$userId")
    }

    fun addUserToActiveList(user: User) {
        customUserRepository.addToUserList("active_users", user)
    }

}