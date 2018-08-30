package com.poc.spring.security.with.jwt

import com.poc.spring.security.with.jwt.infrastruct.utils.MessageUtils.Companion.BASE_NAME
import com.poc.spring.security.with.jwt.infrastruct.utils.MessageUtils.Companion.DEFAULT_ENCODING
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
open class Application

fun main(args: Array<String>) {

    @Bean
    fun messageSource(): MessageSource {

        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename(BASE_NAME)
        messageSource.setDefaultEncoding(DEFAULT_ENCODING)

        return messageSource
    }

    runApplication<Application>(*args)
}
