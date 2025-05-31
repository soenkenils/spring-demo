package me.soenke.spring_demo.dadjoke.repository

import me.soenke.spring_demo.dadjoke.model.DadJoke
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.Instant
import java.util.*

@Repository
class DadJokeRepository(private val jdbcTemplate: JdbcTemplate) {
    
    private val rowMapper = RowMapper<DadJoke> { rs: ResultSet, _: Int ->
        DadJoke(
            id = rs.getLong("id"),
            jokeText = rs.getString("joke_text"),
            createdAt = rs.getTimestamp("created_at").toInstant(),
            updatedAt = rs.getTimestamp("updated_at").toInstant()
        )
    }
    
    fun findRandomJoke(): DadJoke? {
        return jdbcTemplate.query(
            "SELECT * FROM dad_jokes ORDER BY RANDOM() LIMIT 1",
            rowMapper
        ).firstOrNull()
    }
    
    fun findAll(): List<DadJoke> {
        return jdbcTemplate.query("SELECT * FROM dad_jokes", rowMapper)
    }
    
    fun findById(id: Long): DadJoke? {
        return jdbcTemplate.query(
            "SELECT * FROM dad_jokes WHERE id = ?",
            rowMapper,
            id
        ).firstOrNull()
    }
    
    fun save(joke: DadJoke): DadJoke {
        return if (joke.id == null) {
            val id = jdbcTemplate.queryForObject(
                """
                INSERT INTO dad_jokes (joke_text, created_at, updated_at)
                VALUES (?, ?, ?)
                RETURNING id
                """.trimIndent(),
                Long::class.java,
                joke.jokeText,
                joke.createdAt,
                joke.updatedAt
            )
            joke.copy(id = id)
        } else {
            jdbcTemplate.update(
                """
                UPDATE dad_jokes 
                SET joke_text = ?, updated_at = ?
                WHERE id = ?
                """.trimIndent(),
                joke.jokeText,
                Instant.now(),
                joke.id
            )
            joke.copy(updatedAt = Instant.now())
        }
    }
    
    fun deleteById(id: Long): Boolean {
        return jdbcTemplate.update("DELETE FROM dad_jokes WHERE id = ?", id) > 0
    }
}
