package lib_spring_data.redis_publish.socket.v1

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import lib_spring_data.redis_publish.socket.ChatWebSocketHandler
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

class ChatWebSocketNormalHandler(private val chatRoomManager: ChatRoomManager) : ChatWebSocketHandler() {

    private val objectMapper = jacksonObjectMapper()

    private val sessions = ConcurrentHashMap<String, WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions[session.id] = session
        println("새 연결: ${session.id}")

        val welcomeMessage = mapOf(
            "type" to "WELCOME",
            "message" to "WebSocket 서버에 연결되었습니다!",
            "sessionId" to session.id
        )

        sendMessage(session, welcomeMessage)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {

        val payload = message.payload
        println("메시지 수신 : $payload")

        val messageData = objectMapper.readValue<Map<String, Any>>(payload)

        when (messageData["type"]) {
            "JOIN" -> handlerJoinMessage(session, messageData)
            "CHAT" -> handlerChatMessage(session, messageData)
            "PING" -> handlerPingMessage(session)
            "BROADCAST" -> handlerBroadcastMessage(session, messageData)
            else -> handlerUnknownMessage(session, messageData)
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session.id)
        println("연결 해제 : ${session.id}, 상태 : ${status.code}")
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        println("연결 예외 발생 ${exception.message}")
    }


    private fun handlerJoinMessage(session: WebSocketSession, messageData: Map<String, Any>){
        val roomNumber = (messageData["room_number"] as? String)?.toIntOrNull() ?: run {
            println("방 번호는 숫자로 입력되어야 합니다.")
            return
        }

        chatRoomManager.joinRoom(roomNumber, session)
    }

    private fun handlerChatMessage(session: WebSocketSession, messageData: Map<String, Any>){
         val chatMessage = mapOf(
            "type" to "CHAT_RESPONSE",
            "from" to session.id,
            "content" to messageData["content"],
            "timestamp" to System.currentTimeMillis()
        )

        // 자신이 속한 채팅방에게 메시지 전송
        chatRoomManager.sendMessage(session, chatMessage)
//        broadcastToAll(chatMessage)
    }

    private fun handlerPingMessage(session: WebSocketSession){
        val pongMessage = mapOf(
            "type" to "PONG",
            "timestamp" to System.currentTimeMillis()
        )
        sendMessage(session, pongMessage)
    }

    private fun handlerBroadcastMessage(session: WebSocketSession, messageData: Map<String, Any>){
        val broadcastMessage = mapOf(
            "type" to "BROADCAST",
            "content" to messageData["content"],
            "timestamp" to System.currentTimeMillis()
        )
        broadcastToAll(broadcastMessage)
    }

    private fun handlerUnknownMessage(session: WebSocketSession, messageData: Map<String, Any>){
        val errorMessage = mapOf(
            "type" to "ERROR",
            "message" to "알 수 없는 메시지 타입: ${messageData["type"]}"
        )
        sendMessage(session, errorMessage)
    }

    private fun sendMessage(session: WebSocketSession, message: Any) {
        if (session.isOpen) {
            val json = objectMapper.writeValueAsString(message)
            session.sendMessage(TextMessage(json))
        }
    }

    private fun broadcastToAll(chatMessage: Map<String, Any?>) {
        val json = objectMapper.writeValueAsString(chatMessage)
        sessions.values.forEach { session ->
            try {
                if (session.isOpen) {
                    session.sendMessage(TextMessage(json))
                }
            } catch (e: Exception) {
                println("브로드캐스트 실패: ${session.id}")
                sessions.remove(session.id)
            }
        }
    }
}