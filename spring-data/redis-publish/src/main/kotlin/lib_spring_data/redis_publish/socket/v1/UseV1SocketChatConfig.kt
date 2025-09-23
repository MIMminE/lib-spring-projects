package lib_spring_data.redis_publish.socket.v1

import lib_spring_data.redis_publish.config.WebSocketChatV1Config
import lib_spring_data.redis_publish.config.WebSocketRegistryConfig
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(WebSocketChatV1Config::class, WebSocketRegistryConfig::class)
annotation class UseV1SocketChatConfig()
