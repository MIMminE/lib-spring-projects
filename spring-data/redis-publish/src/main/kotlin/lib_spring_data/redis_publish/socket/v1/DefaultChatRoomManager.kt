package lib_spring_data.redis_publish.socket.v1

import lib_spring_data.redis_publish.socket.ChatRoomManager
import lib_spring_data.redis_publish.socket.MessageHandler
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

class DefaultChatRoomManager(
    private val messageHandler: MessageHandler,
    private val rooms: HashMap<String, MutableList<WebSocketSession>> = HashMap(),
    private val sessionToRoomNumber: ConcurrentHashMap<String, String> = ConcurrentHashMap()
) : ChatRoomManager {

    override fun joinRoom(roomNumber: String, session: WebSocketSession) {
        rooms.getOrPut(roomNumber) { mutableListOf() }.add(session)
        sessionToRoomNumber[session.id] = roomNumber
        messageHandler.sendWelcomeMessage(session)
    }

    override fun sendMessage(session: WebSocketSession, message: TextMessage) {
        sessionToRoomNumber[session.id]?.let { roomNumber ->
            rooms[roomNumber]?.let { messageHandler.sendMessageForSessions(it, message) }
        }
    }

    override fun remove(session: WebSocketSession) {
        sessionToRoomNumber.remove(session.id)
    }
}