package me.soenke.spring_demo

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Implementation of the NameService interface.
 */
@Service
class NameService {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val names = mutableSetOf<String>()

    /**
     * Creates a new name if it doesn't already exist.
     *
     * @param name The name to create
     * @return Result indicating success or failure with reason
     */
    fun createName(name: String): Result<Unit> {
        logger.debug("Attempting to create name: {}", mapOf("name" to name))

        return if (names.contains(name)) {
            logger.info("Name already exists: {}", mapOf("name" to name))
            Result.Failure("Name already exists")
        } else {
            names.add(name)
            logger.info("Name created successfully: {}", mapOf("name" to name))
            Result.Success(Unit)
        }
    }
}
