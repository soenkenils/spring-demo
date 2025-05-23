package me.soenke.spring_demo.name

import io.kotest.core.spec.style.ShouldSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.HttpStatus

class NameControllerTest : ShouldSpec({
    val nameService = mockk<NameService>()
    val nameController = NameController(nameService)

    beforeTest {
        clearAllMocks()
    }

    should("return CREATED status when name is successfully created") {
        // Given
        val nameRequest = NameRequest("John")
        every { nameService.createName("John") } returns Result.Success(Unit)

        // When
        val response = nameController.createName(nameRequest)

        // Then
        assert(response.statusCode == HttpStatus.CREATED)
        assert(response.body?.name == "John")
        assert(response.body?.message == "Name created successfully")
        verify(exactly = 1) { nameService.createName("John") }
    }

    should("return CONFLICT status when name already exists") {
        // Given
        val nameRequest = NameRequest("Jane")
        every { nameService.createName("Jane") } returns Result.Failure("Name already exists")

        // When
        val response = nameController.createName(nameRequest)

        // Then
        assert(response.statusCode == HttpStatus.CONFLICT)
        assert(response.body?.name == "Jane")
        assert(response.body?.message == "Name already exists")
        verify(exactly = 1) { nameService.createName("Jane") }
    }

    should("return BAD_REQUEST status for other errors") {
        // Given
        val nameRequest = NameRequest("Invalid#Name")
        every { nameService.createName("Invalid#Name") } returns Result.Failure("Invalid name format")

        // When
        val response = nameController.createName(nameRequest)

        // Then
        assert(response.statusCode == HttpStatus.BAD_REQUEST)
        assert(response.body?.name == "Invalid#Name")
        assert(response.body?.message == "Invalid name format")
        verify(exactly = 1) { nameService.createName("Invalid#Name") }
    }
})
