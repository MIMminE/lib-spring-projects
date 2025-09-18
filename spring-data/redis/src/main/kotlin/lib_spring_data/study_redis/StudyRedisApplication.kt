package lib_spring_data.study_redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["lib_spring_data.study_redis.redis_session"])
//@SpringBootApplication(scanBasePackages = ["lib_spring_data.study_redis.redis_distributed_lock"])
class StudyRedisApplication()

fun main(args: Array<String>) {
    runApplication<StudyRedisApplication>(*args)
}


데이터 크기별 조회 시간:

Redis (Hash Table):
- 1,000개: O(1) = 0.1ms
- 1,000,000개: O(1) = 0.1ms  ← 크기와 무관!
- 1,000,000,000개: O(1) = 0.1ms

MySQL (B+ Tree + 디스크):
- 1,000개: O(log n) = 1ms + 10ms(디스크) = 11ms
- 1,000,000개: O(log n) = 3ms + 10ms(디스크) = 13ms  
- 1,000,000,000개: O(log n) = 5ms + 10ms(디스크) = 15ms