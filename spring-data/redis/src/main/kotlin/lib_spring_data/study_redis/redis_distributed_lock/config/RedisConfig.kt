package lib_spring_data.study_redis.redis_distributed_lock.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfig(val test: String = "test!"): CommandLineRunner{
    override fun run(vararg args: String?) {
        println("distributed-lock configuration $test")
    }
}