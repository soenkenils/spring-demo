package me.soenke.spring_demo.weather

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * Controller for translating weather data into outfit moods.
 */
@RestController
class WeatherMoodController(private val weatherMoodService: WeatherMoodService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Translates weather data into an outfit mood.
     *
     * @param weatherRequest The weather data provided by the client.
     * @return ResponseEntity containing the outfit mood.
     */
    @PostMapping("/weather-mood")
    fun getOutfitMood(@Valid @RequestBody weatherRequest: WeatherRequest): ResponseEntity<WeatherMoodResponse> {
        logger.info("Processing weather mood request: temperature={}, condition={}", weatherRequest.temperature, weatherRequest.condition)
        
        try {
            val mood = weatherMoodService.determineOutfitMood(weatherRequest)
            logger.info("Generated outfit mood: mood={}", mood)
            return ResponseEntity.ok(WeatherMoodResponse(mood))
        } catch (e: Exception) {
            logger.error("Error processing weather mood: message={}", e.message, e)
            throw e
        }
    }
}

/**
 * Request body for weather data.
 *
 * @property temperature The current temperature in Celsius.
 * @property condition The current weather condition (e.g., sunny, rainy).
 */
data class WeatherRequest(
    @field:NotNull val temperature: Int,
    @field:NotBlank val condition: String
)

/**
 * Response body for outfit mood.
 *
 * @property mood The suggested outfit mood based on the weather.
 */
data class WeatherMoodResponse(val mood: String)
