package lib_spring_data.redis_publish.socket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

class MessageHandler(private val objectMapper: ObjectMapper) {

    fun sendWelcomeMessage(session: WebSocketSession) {
        val welcomeMessage = mapOf(
            "type" to "WELCOME",
            "message" to "WebSocket 서버에 연결되었습니다!",
            "sessionId" to session.id
        )

        sendMessage(session, welcomeMessage)
    }

    fun sendMessageForSessions(sessions: List<WebSocketSession>, message: TextMessage) {
        val payload = message.payload
        try {
            val messageData = objectMapper.readValue<Map<String, Any>>(payload)
            sessions.forEach { sendMessage(it, messageData) }
        } catch (e: Exception) {
            println("메시지 파싱 오류: ${e.message}")
        }
    }

    private fun sendMessage(session: WebSocketSession, message: Any) {
        if (session.isOpen) {
            try {
                val json = objectMapper.writeValueAsString(message)
                session.sendMessage(TextMessage(json))
                println("메시지 전송 성공: $json")
            } catch (e: Exception) {
                println("메시지 전송 오류: ${e.message}")
            }
        }
    }
}