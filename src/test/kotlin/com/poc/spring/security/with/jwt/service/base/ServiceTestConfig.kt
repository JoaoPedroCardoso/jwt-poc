package com.poc.spring.security.with.jwt.service.base

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["com.poc.spring.security.with.jwt", "com.poc.spring.security.with.jwt.reposiroty"])
open class ServiceTestConfig