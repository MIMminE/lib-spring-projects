package lib_spring_data.redis_publish.socket.v2

import com.fasterxml.jackson.databind.ObjectMapper
import lib_spring_data.redis_publish.socket.ChatRoomManager
import lib_spring_data.redis_publish.socket.MessageHandler
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

class RedisChatRoomManager(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val messageHandler: MessageHandler,
    private val sessionMap: ConcurrentHashMap<String, SocketNumberContainer> = ConcurrentHashMap()
) : ChatRoomManager {

    override fun joinRoom(roomNumber: String, session: WebSocketSession) {
        sessionMap[session.id] = SocketNumberContainer(roomNumber, session) // 메모리에서 관리되는 소켓 세션 보관 용도
        messageHandler.sendWelcomeMessage(session)
    }

    override fun sendMessage(session: WebSocketSession, message: TextMessage) {

        val roomNumber = sessionMap[session.id]?.roomNumber
        val channel = "room_$roomNumber"

        // 세션 ID와 메시지를 함께 전달
        val messageData = mapOf(
            "sessionId" to session.id,
            "content" to message.payload,
            "timestamp" to System.currentTimeMillis()
        )

        // Redis Pub/Sub으로 메시지 발행
        redisTemplate.opsForValue()[channel] = objectMapper.writeValueAsString(messageData)
        redisTemplate.convertAndSend(channel, objectMapper.writeValueAsString(messageData))
    }

    override fun remove(session: WebSocketSession) {
        sessionMap.remove(session.id)
    }


    data class SocketNumberContainer(val roomNumber: String, val session: WebSocketSession)
}