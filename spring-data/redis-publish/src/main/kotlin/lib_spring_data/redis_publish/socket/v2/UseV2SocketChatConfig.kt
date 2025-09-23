package lib_spring_data.redis_publish.socket.v2

import lib_spring_data.redis_publish.config.WebSocketChatV2Config
import lib_spring_data.redis_publish.config.WebSocketRegistryConfig
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(WebSocketChatV2Config::class, WebSocketRegistryConfig::class)
annotation class UseV2SocketChatConfig()
