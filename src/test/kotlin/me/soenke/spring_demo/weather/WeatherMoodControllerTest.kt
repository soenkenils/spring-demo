package me.soenke.spring_demo.weather

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class WeatherMoodControllerTest : ShouldSpec({

    context("WeatherMoodController") {
        val weatherMoodService = mockk<WeatherMoodService>()
        val controller = WeatherMoodController(weatherMoodService)

        should("return appropriate response when service returns mood") {
            // Given
            val request = WeatherRequest(temperature = 15, condition = "sunny")
            val expectedMood = "casual"
            every { weatherMoodService.determineOutfitMood(request) } returns expectedMood

            // When
            val response = controller.getOutfitMood(request)

            // Then
            response.statusCode shouldBe HttpStatus.OK
            response.body?.mood shouldBe expectedMood
            verify(exactly = 1) { weatherMoodService.determineOutfitMood(request) }
        }

        should("propagate exceptions from service layer") {
            // Given
            val request = WeatherRequest(temperature = -100, condition = "extreme")
            val exception = IllegalArgumentException("Temperature out of valid range")
            every { weatherMoodService.determineOutfitMood(request) } throws exception

            // When/Then
            shouldThrow<IllegalArgumentException> {
                controller.getOutfitMood(request)
            }
            verify(exactly = 1) { weatherMoodService.determineOutfitMood(request) }
        }
    }
})
