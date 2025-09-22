package lib_spring_data.redis_publish.socket.v2

import com.fasterxml.jackson.databind.ObjectMapper
import lib_spring_data.redis_publish.socket.ChatRoomManager
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

class RedisChatRoomManager(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val sessionMap: ConcurrentHashMap<String, SocketNumberContainer> = ConcurrentHashMap()
) : ChatRoomManager {

    override fun joinRoom(roomNumber: String, session: WebSocketSession) {
        sessionMap[session.id] = SocketNumberContainer(roomNumber, session) // 메모리에서 관리되는 소켓 세션 보관 용도
    }

    override fun sendMessage(session: WebSocketSession, message: TextMessage) {

        val roomNumber = sessionMap[session.id]?.roomNumber
        val key = "roomNumber:$roomNumber"

        redisTemplate.opsForValue()[key] = objectMapper.writeValueAsString(message)
    }

    override fun remove(session: WebSocketSession) {
        sessionMap.remove(session.id)
    }

    data class SocketNumberContainer(val roomNumber: String, val session: WebSocketSession)
}