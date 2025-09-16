package lib_spring_data.study_redis.service

import org.springframework.stereotype.Service

@Service
class SampleService {

    fun test1(): String {
        println("test")
        return "test"
    }

    fun test2(id :Long): String {
        return "$id test"
    }
}