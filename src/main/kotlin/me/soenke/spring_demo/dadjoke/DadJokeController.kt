package me.soenke.spring_demo.dadjoke

import me.soenke.spring_demo.dadjoke.model.DadJoke
import me.soenke.spring_demo.dadjoke.repository.DadJokeRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * Controller for generating random dad jokes.
 * Provides endpoints to retrieve random dad jokes from the database.
 */
@RestController
class DadJokeController(
    private val dadJokeRepository: DadJokeRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Returns a random dad joke from the database.
     * @return ResponseEntity containing a random dad joke.
     * @throws ResponseStatusException with 404 status if no jokes are found
     */
    @GetMapping("/dad-jokes")
    fun getRandomDadJoke(): ResponseEntity<DadJokeResponse> {
        val randomJoke = dadJokeRepository.findRandomJoke()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No dad jokes found in the database")
            
        logger.info(
            "Serving random dad joke. ID={}, Length={}",
            randomJoke.id,
            randomJoke.jokeText.length
        )
        
        return ResponseEntity.ok(DadJokeResponse(randomJoke.jokeText))
    }
}

data class DadJokeResponse(val joke: String)
