package me.soenke.spring_demo.dadjoke

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for generating random dad jokes.
 * Provides endpoints to retrieve random dad jokes from a curated list.
 */
@RestController
class DadJokeController {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val dadJokes = listOf(
        "Why don't skeletons fight each other? They don't have the guts.",
        "What do you call cheese that isn't yours? Nacho cheese.",
        "Why couldn't the bicycle stand up by itself? It was two tired.",
        "What do you call fake spaghetti? An impasta."
    )

    /**
     * Returns a random dad joke.
     * @return ResponseEntity containing a random dad joke.
     */
    @GetMapping("/dad-jokes")
    fun getRandomDadJoke(): ResponseEntity<DadJokeResponse> {
        val randomJoke = dadJokes.random()
        logger.info(
            "Serving random dad joke. Total jokes available={}, jokeLength={}",
            dadJokes.size,
            randomJoke.length
        )
        return ResponseEntity.ok(DadJokeResponse(randomJoke))
    }
}

data class DadJokeResponse(val joke: String)
