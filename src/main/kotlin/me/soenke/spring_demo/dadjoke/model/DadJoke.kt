package me.soenke.spring_demo.dadjoke.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("dad_jokes")
data class DadJoke(
    @Id
    val id: Long? = null,
    
    @Column("joke_text")
    val jokeText: String,
    
    @Column("created_at")
    val createdAt: Instant = Instant.now(),
    
    @Column("updated_at")
    val updatedAt: Instant = Instant.now()
)
