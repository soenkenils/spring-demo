package me.soenke.spring_demo

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import me.soenke.spring_demo.config.TestContainerConfig
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@Import(TestContainerConfig::class)
@ActiveProfiles("test")
class SpringDemoApplicationTests(val applicationContext: ApplicationContext) : ShouldSpec({

    context("Spring application") {
        should("load context successfully") {
            // This test passes if the Spring context loads successfully
            applicationContext.containsBean("springDemoApplication") shouldBe true
        }
    }
})
