package lib_spring_data.study_redis.redis_session.controller

import lib_spring_data.study_redis.redis_session.dto.CreateUserRequest
import lib_spring_data.study_redis.redis_session.dto.UserResponse
import lib_spring_data.study_redis.redis_session.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        val user = userService.createUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<UserResponse> {
        return userService.findById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.findAllUser())
    }

    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<UserResponse> {
        return userService.findByEmail(email)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @PostMapping("/{id}/session")
    fun createSession(
        @PathVariable id: String,
        @RequestBody sessionData: Map<String, Any>
    ): ResponseEntity<String> {
        userService.cacheUserSession(id, sessionData)
        return ResponseEntity.ok("세션이 생성되었습니다")
    }

    @GetMapping("/{id}/session")
    fun getSession(@PathVariable id: String): ResponseEntity<Map<String, Any>> {
        val session = userService.getUserSession(id)
        return ResponseEntity.ok(session)
    }
}