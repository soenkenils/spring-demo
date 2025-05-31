package me.soenke.spring_demo.dadjoke.model

import java.time.Instant

data class DadJoke(
    val id: Long? = null,
    val jokeText: String,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)
