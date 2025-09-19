package lib_spring_data.redis_publish.socket

import lib_spring_data.redis_publish.config.RedisConfig
import lib_spring_data.redis_publish.socket.v2.ChatRoomRedisManager
import lib_spring_data.redis_publish.socket.v2.ChatWebSocketHandlerToRedis
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(ChatRoomRedisManager::class, ChatWebSocketHandlerToRedis::class, RedisConfig::class)
annotation class UseRedisChat()
