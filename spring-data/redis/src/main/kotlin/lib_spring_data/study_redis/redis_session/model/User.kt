package lib_spring_data.study_redis.redis_session.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDateTime

@RedisHash("user", timeToLive = 3600)
data class User(
    @Id
    val id: String? = null, // id 가 null를 허용한다???

    @Indexed
    val email: String,

    val name: String,
    val age: Int,
    val createdAt: LocalDateTime = LocalDateTime.now()
)