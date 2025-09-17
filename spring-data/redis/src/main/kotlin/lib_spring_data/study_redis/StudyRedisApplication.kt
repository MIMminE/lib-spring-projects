package lib_spring_data.study_redis

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StudyRedisApplication() : CommandLineRunner {

    override fun run(vararg args: String?) {

    }
}

fun main(args: Array<String>) {
    runApplication<StudyRedisApplication>(*args)
}
