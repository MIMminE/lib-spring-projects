package lib_spring_data.redis_publish

import lib_spring_data.redis_publish.socket.UseRedisChat
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@UseRedisChat
class RedisPublishApplication

fun main(args: Array<String>) {
	runApplication<RedisPublishApplication>(*args)
}
