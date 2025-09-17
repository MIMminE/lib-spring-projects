package lib_spring_data.study_redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["lib_spring_data.study_redis.redis_session"])
//@SpringBootApplication(scanBasePackages = ["lib_spring_data.study_redis.redis_distributed_lock"])
class StudyRedisApplication()

fun main(args: Array<String>) {
    runApplication<StudyRedisApplication>(*args)
}
