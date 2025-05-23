package me.soenke.spring_demo.example

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import me.soenke.spring_demo.config.TestContainerConfig
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

/**
 * Example of a Kotest ShouldSpec test for service components.
 * This demonstrates the proper style for writing tests according to team preferences.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Import(TestContainerConfig::class)
@ActiveProfiles("test")
class ExampleServiceTest : ShouldSpec({
    
    context("ExampleService") {
        should("return correct greeting when name is provided") {
            val exampleService = ExampleService()
            val result = exampleService.greet("World")
            result shouldBe "Hello, World!"
        }
        
        should("return default greeting when name is empty") {
            val exampleService = ExampleService()
            val result = exampleService.greet("")
            result shouldBe "Hello, Guest!"
        }
    }
    
})

/**
 * Simple service class for demonstration purposes.
 */
class ExampleService {
    fun greet(name: String): String {
        return if (name.isNotBlank()) {
            "Hello, $name!"
        } else {
            "Hello, Guest!"
        }
    }
}
