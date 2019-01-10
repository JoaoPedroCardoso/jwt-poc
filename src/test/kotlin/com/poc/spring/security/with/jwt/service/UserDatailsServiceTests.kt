package com.poc.spring.security.with.jwt.service

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.poc.spring.security.with.jwt.ApplicationTests
import com.poc.spring.security.with.jwt.reposiroty.UserRepository
import com.poc.spring.security.with.jwt.service.base.ServiceBaseTest
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserDatailsServiceTests : ServiceBaseTest() {

    lateinit var userDetailsService: UserDetailsService
    private val userRepository: UserRepository = mock()

    @Before
    fun init() {
        userDetailsService = UserDetailsService(userRepository = userRepository)
    }

    @Test
    fun `load user by userName with success`() {
        whenever(userRepository.findByUserName("vict")).thenReturn(buildUser())
        val user = userDetailsService.loadUserByUsername("vict")
        TestCase.assertEquals("vict", user.userName)
    }

    @Test
    fun `load user by invalid userName`() {
        whenever(userRepository.findByUserName("vict")).thenReturn(null)
        val exception = Assertions.assertThrows(UsernameNotFoundException::class.java) {
            userDetailsService.loadUserByUsername("blabla")
        }
        TestCase.assertEquals("blabla", exception.message)
    }
}
