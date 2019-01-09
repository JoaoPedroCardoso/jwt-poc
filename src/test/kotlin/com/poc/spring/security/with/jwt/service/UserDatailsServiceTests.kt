package com.poc.spring.security.with.jwt.service

import com.poc.spring.security.with.jwt.ApplicationTests
import junit.framework.TestCase
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserDatailsServiceTests : ApplicationTests() {

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Test
    fun `load user by userName with success`() {
        val user = userDetailsService.loadUserByUsername("vict")
        TestCase.assertEquals("vict", user.userName)
    }

    @Test
    fun `load user by invalid userName`() {
        val exception = Assertions.assertThrows(UsernameNotFoundException::class.java) {
            userDetailsService.loadUserByUsername("blabla")
        }
        TestCase.assertEquals("blabla", exception.message)
    }
}
