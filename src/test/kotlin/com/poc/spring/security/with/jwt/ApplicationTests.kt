package com.poc.spring.security.with.jwt

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(Application::class)
open class ApplicationTests
