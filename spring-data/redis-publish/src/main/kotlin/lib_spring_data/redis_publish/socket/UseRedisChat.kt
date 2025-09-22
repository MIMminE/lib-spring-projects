package lib_spring_data.redis_publish.socket

import lib_spring_data.redis_publish.config.RedisConfig
import lib_spring_data.redis_publish.socket.v2.RedisChatRoomManager
import lib_spring_data.redis_publish.socket.v2.RedisChatWebSocketHandler
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(RedisChatRoomManager::class, RedisChatWebSocketHandler::class, RedisConfig::class)
annotation class UseRedisChat()
