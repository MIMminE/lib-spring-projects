package lib_spring_data.redis_publish.socket

import lib_spring_data.redis_publish.socket.v1.DefaultChatRoomManager
import lib_spring_data.redis_publish.socket.v1.DefaultChatWebSocketHandler
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(DefaultChatRoomManager::class, DefaultChatWebSocketHandler::class)
annotation class UseNormalSocketChat()
