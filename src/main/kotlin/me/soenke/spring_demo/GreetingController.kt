package me.soenke.spring_demo

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/greetings")
    fun getRandomGreeting(): ResponseEntity<GreetingResponse> {
        val greeting = GREETINGS.random()
        logger.info("Generated random greeting: message={}", greeting)
        return ResponseEntity.ok(GreetingResponse(greeting))
    }

    companion object {
        val GREETINGS = listOf("Hi!", "Hello", "Hey there!", "Greetings!", "Howdy!")
    }
}

data class GreetingResponse(
    val message: String
)
