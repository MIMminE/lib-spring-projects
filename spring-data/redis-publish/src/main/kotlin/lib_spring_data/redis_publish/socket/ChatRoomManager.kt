package lib_spring_data.redis_publish.socket

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

interface ChatRoomManager {

    fun joinRoom(roomNumber: String, session: WebSocketSession)
    fun sendMessage(session: WebSocketSession, message: TextMessage)
    fun remove(session: WebSocketSession)

}