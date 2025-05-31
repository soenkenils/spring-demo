package me.soenke.spring_demo.dadjoke.repository

import me.soenke.spring_demo.dadjoke.model.DadJoke
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DadJokeRepository : CrudRepository<DadJoke, Long> {
    
    @Query("SELECT * FROM dad_jokes ORDER BY RANDOM() LIMIT 1")
    fun findRandomJoke(): DadJoke?
}
