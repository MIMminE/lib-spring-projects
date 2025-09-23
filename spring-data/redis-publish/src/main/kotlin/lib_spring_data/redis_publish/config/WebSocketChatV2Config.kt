package lib_spring_data.redis_publish.config

import com.fasterxml.jackson.databind.ObjectMapper
import lib_spring_data.redis_publish.socket.ChatRoomManager
import lib_spring_data.redis_publish.socket.ChatWebSocketHandler
import lib_spring_data.redis_publish.socket.MessageHandler
import lib_spring_data.redis_publish.socket.v2.RedisChatRoomManager
import lib_spring_data.redis_publish.socket.v2.RedisChatWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.web.socket.config.annotation.EnableWebSocket

@EnableWebSocket
@EnableRedisRepositories
class WebSocketChatV2Config {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(RedisStandaloneConfiguration("localhost", 6379))
    }

    @Bean
    fun messageHandler(objectMapper: ObjectMapper): MessageHandler {
        return MessageHandler(objectMapper)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.connectionFactory = redisConnectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()
        template.afterPropertiesSet()
        return template
    }

    @Bean
    fun redisMessageListener(redisConnectionFactory: RedisConnectionFactory): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(redisConnectionFactory)
        // 채널 이름을 지정해서 리스너 등록
        container.addMessageListener(MessageListener { message, pattern ->
            val channelName = String(message.channel)
            val messageBody = String(message.body)
            println("채널: $channelName, 메시지: $messageBody")
        }, PatternTopic("room_*"))
        return container
    }

    @Bean
    fun chatRoomManager(
        redisTemplate: RedisTemplate<String, String>,
        objectMapper: ObjectMapper,
        messageHandler: MessageHandler
    ): ChatRoomManager {
        return RedisChatRoomManager(redisTemplate, objectMapper, messageHandler)
    }

    @Bean
    fun chatWebSocketHandler(chatRoomManager: ChatRoomManager): ChatWebSocketHandler {
        return RedisChatWebSocketHandler(chatRoomManager)
    }
}