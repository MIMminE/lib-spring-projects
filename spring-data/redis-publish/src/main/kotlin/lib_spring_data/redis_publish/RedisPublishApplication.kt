package lib_spring_data.redis_publish

import lib_spring_data.redis_publish.socket.v2.UseV2SocketChatConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@UseV2SocketChatConfig
//@UseV1SocketChatConfig
class RedisPublishApplication

fun main(args: Array<String>) {
    runApplication<RedisPublishApplication>(*args)
}
