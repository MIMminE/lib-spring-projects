package lib_spring_data.study_redis

import lib_spring_data.study_redis.service.SampleService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StudyRedisApplication(val service: SampleService) : CommandLineRunner {

    override fun run(vararg args: String?) {

        service.test1()
    }
}

fun main(args: Array<String>) {
    runApplication<StudyRedisApplication>(*args)
}
