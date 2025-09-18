package lib_spring_data.redis_publish

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RedisPublishApplication

fun main(args: Array<String>) {
	runApplication<RedisPublishApplication>(*args)
}
