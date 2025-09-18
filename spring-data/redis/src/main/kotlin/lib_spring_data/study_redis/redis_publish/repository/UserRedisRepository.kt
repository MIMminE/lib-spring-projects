package lib_spring_data.study_redis.redis_publish.repository

import lib_spring_data.study_redis.redis_publish.model.User
import org.springframework.data.repository.CrudRepository

interface UserRedisRepository : CrudRepository<User, String> {
    fun findByEmail(email: String): User?
    fun findByName(name: String): List<User>
    fun findByAgeGreaterThan(age: Int): List<User>
}