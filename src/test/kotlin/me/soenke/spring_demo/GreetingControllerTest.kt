package me.soenke.spring_demo

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeEmpty
import me.soenke.spring_demo.GreetingController.Companion.GREETINGS
import me.soenke.spring_demo.config.TestContainerConfig
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfig::class)
@ActiveProfiles("test")
class GreetingControllerTest(val restTemplate: TestRestTemplate) : ShouldSpec({

    context("GET /greetings") {
        should("return a non-empty greeting") {
            val response = restTemplate.getForEntity("/greetings", String::class.java)

            response.statusCode shouldBe HttpStatus.OK
            response.body!!.shouldNotBeEmpty()
            GREETINGS shouldContain response.body
        }

        should("return 200 OK status") {
            val response = restTemplate.getForEntity("/greetings", String::class.java)
            response.statusCode shouldBe HttpStatus.OK
        }
    }
})
