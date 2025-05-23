package me.soenke.spring_demo.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension

/**
 * Kotest project configuration for Spring integration.
 * This enables the Spring context to be used in Kotest specs.
 */
class KotestProjectConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)
}
