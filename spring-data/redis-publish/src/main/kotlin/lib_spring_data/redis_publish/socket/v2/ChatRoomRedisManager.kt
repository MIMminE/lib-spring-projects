package lib_spring_data.redis_publish.socket.v2

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import lib_spring_data.redis_publish.socket.v2.dto.WebSessionInfo
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

class ChatRoomRedisManager(
    private val redisTemplate: RedisTemplate<String, WebSessionInfo>,
    private val objectMapper: ObjectMapper = jacksonObjectMapper(),
    private val sessionMap: ConcurrentHashMap<String, WebSocketSession>
) {

    // 세션 정보를 저장하는 메서드
    fun saveSession(webSocketSession: WebSocketSession) {
        sessionMap[webSocketSession.id] = webSocketSession
    }

    // 세션 정보를 조회하는 메서드
    fun getSession(sessionId: String): WebSocketSession? {
        val key = "session:$sessionId"
        return sessionMap[key]
    }

    // 방에 속한 세션 정보를 저장하는 메서드
    fun saveRoomSession(roomId: String, webSocketSession: WebSocketSession) {
        val sessionInfo = WebSessionInfo.from(webSocketSession, roomId)
        val key = "session:${webSocketSession.id}"
        redisTemplate.opsForValue()[key] = sessionInfo
    }

    // 방에 속한 세션 정보를 조회하는 메서드
    fun getRoomSession(roomId: String, sessionId: String): WebSessionInfo? {
        val key = "room:$roomId:session:$sessionId"
        return redisTemplate.opsForValue()[key]
    }
}