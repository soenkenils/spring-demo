package me.soenke.spring_demo.config

import org.flywaydb.core.Flyway
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import javax.sql.DataSource

@TestConfiguration(proxyBeanMethods = false)
class TestConfig {
    
    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer("postgres:15-alpine")
            .withDatabaseName("dadjokes_test")
            .withUsername("test")
            .withPassword("test")
            .waitingFor(Wait.forListeningPort())
    }
    
    @Bean(initMethod = "migrate")
    fun flyway(dataSource: DataSource): Flyway {
        return Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .load()
    }
}
