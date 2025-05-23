package me.soenke.spring_demo.dadjoke

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.string.shouldContain
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(DadJokeController::class)
@WithMockUser
class DadJokeControllerTest(val mockMvc: MockMvc) : ShouldSpec({

    should("return a random dad joke") {
        val response = mockMvc.get("/dad-jokes")
            .andExpect { status { isOk() } }
            .andReturn()

        val responseBody = response.response.contentAsString
        responseBody shouldContain "joke"
    }
})
