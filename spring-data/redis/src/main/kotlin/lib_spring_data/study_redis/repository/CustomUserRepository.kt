package lib_spring_data.study_redis.repository

import lib_spring_data.study_redis.model.User
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class CustomUserRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun saveUserData(key: String, userData: Map<String, Any>){
        redisTemplate.opsForHash<String, Any>().putAll("user:$key", userData)
    }

    fun getUserData(key: String): Map<String, Any>{
        return redisTemplate.opsForHash<String, Any>().entries("user:${key}")
    }

    fun addToUserList(listKey: String, user: User) {
        redisTemplate.opsForList().rightPush(listKey, user)
    }

    fun cacheUserWithExpiry(key: String, user: User, ttl: Duration) {
        redisTemplate.opsForValue().set(key, user, ttl)
    }
}

fun main(){
    val customUserRepository = CustomUserRepository(RedisTemplate())



}