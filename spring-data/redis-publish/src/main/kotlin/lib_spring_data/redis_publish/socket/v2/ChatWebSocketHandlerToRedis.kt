package lib_spring_data.redis_publish.socket.v2

import lib_spring_data.redis_publish.socket.ChatWebSocketHandler
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

class ChatWebSocketHandlerToRedis(private val chatRoomRedisManager: ChatRoomRedisManager) : ChatWebSocketHandler(){

    override fun afterConnectionEstablished(session: WebSocketSession) {
        chatRoomRedisManager.saveSession(session )
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        super.handleTextMessage(session, message)
    }
}