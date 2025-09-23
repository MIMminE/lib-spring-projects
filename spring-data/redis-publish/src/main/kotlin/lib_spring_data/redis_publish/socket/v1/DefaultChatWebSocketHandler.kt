package lib_spring_data.redis_publish.socket.v1

import lib_spring_data.redis_publish.socket.ChatRoomManager
import lib_spring_data.redis_publish.socket.ChatWebSocketHandler
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

class DefaultChatWebSocketHandler(
    private val chatRoomManager: ChatRoomManager
) : ChatWebSocketHandler() {

    private val sessions = ConcurrentHashMap<String, WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {

        sessions[session.id] = session
        val uri = session.uri
        val roomId = uri?.query?.split("&")?.find { it.startsWith("roomId=") }?.split("=")?.get(1)

        roomId?.let { chatRoomManager.joinRoom(it, session) }
            ?: run { throw IllegalArgumentException("방 번호가 없습니다.") }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        chatRoomManager.sendMessage(session, message)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session.id)
        println("연결 해제 : ${session.id}, 상태 : ${status.code}")
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        println("연결 예외 발생 ${exception.message}")
    }
}