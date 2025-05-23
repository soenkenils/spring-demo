package me.soenke.spring_demo.example

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

/**
 * Example of a pure Kotest ShouldSpec test without Spring integration.
 * This demonstrates how to test simple components that don't require the Spring context.
 */
class ExampleServiceUnitTest : ShouldSpec({

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
