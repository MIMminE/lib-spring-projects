package lib_spring_data.redis_publish.socket

import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class ChatRoomManager(
    private val rooms: HashMap<Int, MutableList<WebSocketSession>> = HashMap(),
    private val sessionToRoomNumber: ConcurrentHashMap<String, Int> = ConcurrentHashMap()
) {

    fun joinRoom(roomNumber: Int, session: WebSocketSession) {
        rooms.getOrPut(roomNumber) { mutableListOf() }.add(session)
        sessionToRoomNumber[session.id] = roomNumber
    }

    fun sendMessage(session: WebSocketSession, message: Any) {
        sessionToRoomNumber[session.id]?.let { roomNumber ->
            rooms[roomNumber]?.forEach { targetSession ->
                if (targetSession.isOpen) {
                    targetSession.sendMessage(TextMessage(message.toString()))
                }
            }
        }
    }

}

