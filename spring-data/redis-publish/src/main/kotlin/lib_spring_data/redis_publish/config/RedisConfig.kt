package lib_spring_data.redis_publish.config

import lib_spring_data.redis_publish.socket.v2.dto.WebSessionInfo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@EnableRedisRepositories
@Configuration
class RedisConfig {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(RedisStandaloneConfiguration("localhost", 6379))
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, WebSessionInfo> {
        val template = RedisTemplate<String, WebSessionInfo>()
        template.connectionFactory = redisConnectionFactory()
        template.keySerializer = StringRedisSerializer()

        val serializer = Jackson2JsonRedisSerializer(WebSessionInfo::class.java)
        template.valueSerializer = serializer
        return template
    }
}