package lib_spring_data.redis_publish.socket

import lib_spring_data.redis_publish.socket.v1.ChatRoomManager
import lib_spring_data.redis_publish.socket.v1.ChatWebSocketNormalHandler
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(ChatRoomManager::class, ChatWebSocketNormalHandler::class)
annotation class UseNormalSocketChat()
