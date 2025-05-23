package me.soenke.spring_demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {

    @GetMapping("/greetings")
    fun getRandomGreeting(): String {
        return GREETINGS.random()
    }

    companion object {
        val GREETINGS = listOf("Hi!", "Hello", "Hey there!", "Greetings!", "Howdy!")
    }
}
