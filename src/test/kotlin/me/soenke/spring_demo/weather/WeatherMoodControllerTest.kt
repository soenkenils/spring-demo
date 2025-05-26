package me.soenke.spring_demo.weather

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import me.soenke.spring_demo.config.SecurityConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(WeatherMoodController::class)
@Import(SecurityConfig::class)
class WeatherMoodControllerTest(@Autowired val mockMvc: MockMvc) : ShouldSpec({

    context("WeatherMoodController") {

        should("return 'cozy and warm' for cold weather") {
            val requestBody = """
                {
                    "temperature": 5,
                    "condition": "cloudy"
                }
            """.trimIndent()

            val response = mockMvc.post("/weather-mood") {
                contentType = org.springframework.http.MediaType.APPLICATION_JSON
                content = requestBody
            }.andExpect {
                status { isOk() }
            }.andReturn()

            response.response.contentAsString shouldBe "{\"mood\":\"cozy and warm\"}"
        }

        should("return 'rainy day outfit' for rainy weather") {
            val requestBody = """
                {
                    "temperature": 15,
                    "condition": "rainy"
                }
            """.trimIndent()

            val response = mockMvc.post("/weather-mood") {
                contentType = org.springframework.http.MediaType.APPLICATION_JSON
                content = requestBody
            }.andExpect {
                status { isOk() }
            }.andReturn()

            response.response.contentAsString shouldBe "{\"mood\":\"rainy day outfit\"}"
        }

        should("return 'casual' for mild weather") {
            val requestBody = """
                {
                    "temperature": 15,
                    "condition": "sunny"
                }
            """.trimIndent()

            val response = mockMvc.post("/weather-mood") {
                contentType = org.springframework.http.MediaType.APPLICATION_JSON
                content = requestBody
            }.andExpect {
                status { isOk() }
            }.andReturn()

            response.response.contentAsString shouldBe "{\"mood\":\"casual\"}"
        }

        should("return 'light and breezy' for warm weather") {
            val requestBody = """
                {
                    "temperature": 25,
                    "condition": "sunny"
                }
            """.trimIndent()

            val response = mockMvc.post("/weather-mood") {
                contentType = org.springframework.http.MediaType.APPLICATION_JSON
                content = requestBody
            }.andExpect {
                status { isOk() }
            }.andReturn()

            response.response.contentAsString shouldBe "{\"mood\":\"light and breezy\"}"
        }
    }
})
