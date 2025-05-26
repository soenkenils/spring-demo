package me.soenke.spring_demo.weather

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class WeatherMoodServiceTest : ShouldSpec({
    
    context("WeatherMoodService") {
        val weatherMoodService = WeatherMoodService()
        
        should("return 'cozy and warm' for cold temperatures") {
            val request = WeatherRequest(temperature = 5, condition = "clear")
            weatherMoodService.determineOutfitMood(request) shouldBe "cozy and warm"
        }
        
        should("return 'rainy day outfit' regardless of temperature when condition is rainy") {
            val request = WeatherRequest(temperature = 15, condition = "rainy")
            weatherMoodService.determineOutfitMood(request) shouldBe "rainy day outfit"
        }
        
        should("return 'casual' for mild temperatures") {
            val request = WeatherRequest(temperature = 15, condition = "sunny")
            weatherMoodService.determineOutfitMood(request) shouldBe "casual"
        }
        
        should("return 'light and breezy' for warm temperatures") {
            val request = WeatherRequest(temperature = 25, condition = "clear")
            weatherMoodService.determineOutfitMood(request) shouldBe "light and breezy"
        }
        
        should("return 'extreme winter protection' for extreme cold") {
            val request = WeatherRequest(temperature = -15, condition = "clear")
            weatherMoodService.determineOutfitMood(request) shouldBe "extreme winter protection"
        }
        
        should("return 'minimal and cooling' for extreme heat") {
            val request = WeatherRequest(temperature = 40, condition = "sunny")
            weatherMoodService.determineOutfitMood(request) shouldBe "minimal and cooling"
        }
    }
})
