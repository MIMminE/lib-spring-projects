package lib_spring_data.redis_publish.socket.v2

import lib_spring_data.redis_publish.socket.ChatRoomManager
import lib_spring_data.redis_publish.socket.ChatWebSocketHandler
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

class RedisChatWebSocketHandler(
    private val redisChatRoomManager: ChatRoomManager
) : ChatWebSocketHandler() {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val uri = session.uri
        val roomId = uri?.query?.split("&")?.find { it.startsWith("roomId=") }?.split("=")?.get(1)

        roomId?.let { redisChatRoomManager.joinRoom(it, session) }
            ?: run { throw IllegalArgumentException("방 번호가 없습니다.") }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        redisChatRoomManager.remove(session)
        println("연결 해제 : ${session.id}, 상태 : ${status.code}")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        redisChatRoomManager.sendMessage(session, message)
    }
}