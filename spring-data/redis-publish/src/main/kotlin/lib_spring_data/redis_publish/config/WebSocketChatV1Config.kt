package lib_spring_data.redis_publish.config

import com.fasterxml.jackson.databind.ObjectMapper
import lib_spring_data.redis_publish.socket.ChatRoomManager
import lib_spring_data.redis_publish.socket.ChatWebSocketHandler
import lib_spring_data.redis_publish.socket.MessageHandler
import lib_spring_data.redis_publish.socket.v1.DefaultChatRoomManager
import lib_spring_data.redis_publish.socket.v1.DefaultChatWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.web.socket.config.annotation.EnableWebSocket

@EnableWebSocket
class WebSocketChatV1Config {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }

    @Bean
    fun messageHandler(objectMapper: ObjectMapper): MessageHandler {
        return MessageHandler(objectMapper)
    }

    @Bean
    fun chatRoomManager(messageHandler: MessageHandler): ChatRoomManager {
        return DefaultChatRoomManager(messageHandler)
    }

    @Bean
    fun chatWebSocketHandler(chatRoomManager: ChatRoomManager): ChatWebSocketHandler {
        return DefaultChatWebSocketHandler(chatRoomManager)
    }

}