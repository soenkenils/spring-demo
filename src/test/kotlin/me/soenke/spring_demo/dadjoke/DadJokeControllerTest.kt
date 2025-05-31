package me.soenke.spring_demo.dadjoke

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldNotBe
import me.soenke.spring_demo.config.TestDatabaseConfig
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = [TestDatabaseConfig::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DadJokeControllerTest @Autowired constructor(
    private val mockMvc: MockMvc
) : ShouldSpec({
    
    val objectMapper = jacksonObjectMapper()
    
    context("GET /dad-jokes") {
        should("return a random dad joke with 200 OK") {
            val response = mockMvc.get("/dad-jokes") {
                header("Content-Type", "application/json")
            }.andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$.joke") { 
                    exists()
                    isString()
                }
            }.andReturn()
                .response.contentAsString
            
            val jokeResponse = objectMapper.readValue<Map<String, String>>(response)
            jokeResponse["joke"] shouldNotBe null
        }

        should("return different jokes on subsequent calls") {
            val jokes = mutableSetOf<String>()
            
            repeat(10) {
                val response = mockMvc.get("/dad-jokes") {
                    header("Content-Type", "application/json")
                }.andExpect {
                    status { isOk() }
                    jsonPath("$.joke") { isString() }
                }.andReturn()
                    .response.contentAsString
                
                val jokeResponse = objectMapper.readValue<Map<String, String>>(response)
                jokeResponse["joke"]?.let { jokes.add(it) }
            }
            
            // We should have at least 2 different jokes in our set
            assert(jokes.size > 1) { "Expected at least 2 different jokes, but got ${jokes.size}" }
        }
    }
})
