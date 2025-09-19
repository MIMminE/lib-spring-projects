package lib_spring_data.redis_publish.socket.v2.dto

import org.springframework.web.socket.WebSocketSession

data class WebSessionInfo(
    val sessionId: String,
    val roomId: String,
    val uri: String?,
    val attributes: Map<String, Any>
) {
    companion object {
        fun from(session: WebSocketSession, roomId: String): WebSessionInfo {
            return WebSessionInfo(
                sessionId = session.id,
                roomId = roomId,
                uri = session.uri?.toString(),
                attributes = session.attributes
            )
        }
    }
}
