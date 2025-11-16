package io.kotest.provided

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension

/**
 * Kotest project configuration for Spring integration.
 * This enables the Spring context to be used in Kotest specs.
 *
 * Note: In Kotest 6.0, this file MUST be at io.kotest.provided.ProjectConfig
 * for automatic discovery.
 */
class ProjectConfig : AbstractProjectConfig() {
    override val extensions = listOf(SpringExtension())
}
