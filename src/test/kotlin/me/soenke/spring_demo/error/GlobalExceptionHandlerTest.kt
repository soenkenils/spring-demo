package me.soenke.spring_demo.error

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import jakarta.servlet.http.HttpServletRequest

class GlobalExceptionHandlerTest : ShouldSpec({

    val exceptionHandler = GlobalExceptionHandler()

    context("GlobalExceptionHandler") {
        should("return a 404 error response for NoHandlerFoundException") {
            val mockHttpRequest = mockk<HttpServletRequest>()
            every { mockHttpRequest.requestURI } returns "/non-existent-endpoint"
            
            val webRequest = ServletWebRequest(mockHttpRequest)
            val exception = NoHandlerFoundException("GET", "/non-existent-endpoint", HttpHeaders())
            
            val response = exceptionHandler.handleNotFoundException(exception, webRequest)

            response.statusCode shouldBe HttpStatus.NOT_FOUND
            response.body?.status shouldBe 404
            response.body?.error shouldBe "Not Found"
            response.body?.message shouldNotBe null
            response.body?.path shouldBe "/non-existent-endpoint"
            response.body?.timestamp shouldNotBe null
        }

        should("return a 500 error response for generic exceptions") {
            val mockHttpRequest = mockk<HttpServletRequest>()
            every { mockHttpRequest.requestURI } returns "/test-endpoint"
            
            val webRequest = ServletWebRequest(mockHttpRequest)
            val exception = IllegalStateException("This is a test exception")
            
            val response = exceptionHandler.handleAllExceptions(exception, webRequest)

            response.statusCode shouldBe HttpStatus.INTERNAL_SERVER_ERROR
            response.body?.status shouldBe 500
            response.body?.error shouldBe "Internal Server Error"
            response.body?.message shouldBe "This is a test exception"
            response.body?.path shouldBe "/test-endpoint"
            response.body?.timestamp shouldNotBe null
        }

        should("handle null exception messages gracefully") {
            val mockHttpRequest = mockk<HttpServletRequest>()
            every { mockHttpRequest.requestURI } returns "/test-endpoint"
            
            val webRequest = ServletWebRequest(mockHttpRequest)
            val exception = RuntimeException(null as String?)
            
            val response = exceptionHandler.handleAllExceptions(exception, webRequest)

            response.statusCode shouldBe HttpStatus.INTERNAL_SERVER_ERROR
            response.body?.message shouldBe null
            response.body?.status shouldBe 500
        }
    }
})
