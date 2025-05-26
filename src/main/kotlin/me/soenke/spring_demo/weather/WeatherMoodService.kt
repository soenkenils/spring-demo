package me.soenke.spring_demo.weather

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Service for determining outfit moods based on weather conditions.
 */
@Service
class WeatherMoodService {
    private val logger = LoggerFactory.getLogger(javaClass)
    
    private val EXTREME_COLD_THRESHOLD = -10
    private val COLD_THRESHOLD = 10
    private val MILD_THRESHOLD = 20
    private val EXTREME_HEAT_THRESHOLD = 35
    
    /**
     * Determines an appropriate outfit mood based on weather conditions.
     *
     * @param weatherRequest The weather data to analyze.
     * @return A string describing the outfit mood.
     */
    fun determineOutfitMood(weatherRequest: WeatherRequest): String {
        logger.debug("Determining outfit mood for: temperature={}, condition={}", 
                    weatherRequest.temperature, weatherRequest.condition)
        
        // Validate temperature is within reasonable range
        validateTemperature(weatherRequest.temperature)
        
        return when {
            weatherRequest.temperature < EXTREME_COLD_THRESHOLD -> "extreme winter protection"
            weatherRequest.temperature < COLD_THRESHOLD -> "cozy and warm"
            weatherRequest.condition.equals("rainy", ignoreCase = true) -> "rainy day outfit"
            weatherRequest.condition.equals("snowy", ignoreCase = true) -> "snow appropriate"
            weatherRequest.temperature in COLD_THRESHOLD..MILD_THRESHOLD -> "casual"
            weatherRequest.temperature > EXTREME_HEAT_THRESHOLD -> "minimal and cooling"
            else -> "light and breezy"
        }
    }
    
    private fun validateTemperature(temperature: Int) {
        if (temperature < -50 || temperature > 50) {
            logger.warn("Extreme temperature value detected: temperature={}", temperature)
            // You could throw a custom exception here if needed
        }
    }
}
