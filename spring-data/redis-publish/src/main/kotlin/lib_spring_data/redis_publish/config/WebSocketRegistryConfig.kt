package lib_spring_data.redis_publish.config

import lib_spring_data.redis_publish.socket.ChatWebSocketHandler
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

class WebSocketRegistryConfig(
    private val chatWebSocketHandler: ChatWebSocketHandler
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(chatWebSocketHandler, "/chat")
    }
}