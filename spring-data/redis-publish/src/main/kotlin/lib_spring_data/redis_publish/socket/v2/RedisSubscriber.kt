package lib_spring_data.redis_publish.socket.v2

import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Async

class RedisSubscriber(private val redisTemplate: RedisTemplate<String, String>) {

    @Async
    fun subscribeToChannel(channel: String) {
        println("$channel 구독 시작")
        redisTemplate.execute { connection ->
            connection.subscribe(MessageListener { message, pattern ->
                val channelName = String(message.channel)
                val messageBody = String(message.body)

                println("채널: $channelName, 메시지: $messageBody")

            }, channel.toByteArray())
        }
    }
}