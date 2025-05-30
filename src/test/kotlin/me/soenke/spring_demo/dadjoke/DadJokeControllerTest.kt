package me.soenke.spring_demo.dadjoke

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(DadJokeController::class)
@WithMockUser
class DadJokeControllerTest @Autowired constructor(
    private val mockMvc: MockMvc
) : ShouldSpec({
    
    val objectMapper = jacksonObjectMapper()
    
    context("GET /dad-jokes") {
        should("return a random dad joke with 200 OK") {
            mockMvc.get("/dad-jokes") {
                header("Content-Type", "application/json")
            }.andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$.joke") { 
                    exists()
                    isString()
                }
            }
        }

        should("return a valid JSON response with joke field") {
            mockMvc.get("/dad-jokes") {
                header("Content-Type", "application/json")
            }.andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$.joke") { 
                    exists()
                    isString()
                }
            }
        }

        should("return different jokes on subsequent calls") {
            val jokes = mutableSetOf<String>()
            
            // Make multiple requests and collect unique jokes
            repeat(10) {
                val response = mockMvc.get("/dad-jokes") {
                    header("Content-Type", "application/json")
                }.andExpect {
                    status { isOk() }
                    jsonPath("$.joke") { isString() }
                }.andReturn()
                    .response.contentAsString
                
                // Parse the JSON response to extract the joke
                val joke = objectMapper.readTree(response).get("joke").asText()
                jokes.add(joke)
            }
            
            // There should be at least 2 different jokes in 10 attempts
            (jokes.size > 1) shouldBe true
        }
    }
})
