package com.poc.spring.security.with.jwt.service

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.poc.spring.security.with.jwt.api.request.UserRequest
import com.poc.spring.security.with.jwt.api.request.UserUpdateRequest
import com.poc.spring.security.with.jwt.model.User
import com.poc.spring.security.with.jwt.reposiroty.UserRepository
import com.poc.spring.security.with.jwt.service.base.ServiceBaseTest
import javassist.NotFoundException
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.springframework.context.MessageSource
import java.util.*

class UserServiceTests : ServiceBaseTest() {

    lateinit var userService: UserService

    private val messageSource: MessageSource = mock()
    private val userRepository: UserRepository = mock()

    @Before
    fun init() {
        userService = UserService(userRepository = userRepository, messageSource = messageSource)
    }

    @Test
    fun `find all user with success`() {
        whenever(userRepository.findAll()).thenReturn(buildUserListResponse())
        val list = userService.findAll()
        assertTrue(list.isNotEmpty())
    }

    @Test
    fun `find user by id with success`() {
        whenever(userRepository.findById(1)).thenReturn(buildOptionalUser())
        val user = userService.findById(1)
        assertEquals(1, user.id)
    }

    @Test
    fun `find user by invalid id`() {
        whenever(userRepository.findById(1)).thenReturn(Optional.empty())
        assertThrows(NotFoundException::class.java) {
            userService.findById(0)
        }
    }

    @Test
    fun `find user by userName with success`() {
        whenever(userRepository.findByUserName("vict")).thenReturn(buildUser())
        val user = userService.findByUserName("vict")
        assertEquals("vict", user.userName)
    }

    @Test
    fun `find user by invalid userName`() {
        whenever(userRepository.findByUserName("blabla")).thenReturn(null)
        assertThrows(NotFoundException::class.java) {
            userService.findByUserName("blabla")
        }
    }

    @Test
    fun `find user by email with success`() {
        whenever(userRepository.findByEmail("vito.gomes@gmail.com")).thenReturn(buildUser())
        val user = userService.findByEmail("vito.gomes@gmail.com")
        assertEquals("vito.gomes@gmail.com", user.email)
    }

    @Test
    fun `find user by invalid email`() {
        whenever(userRepository.findByEmail("blabla@gmail.com")).thenReturn(null)
        assertThrows(NotFoundException::class.java) {
            userService.findByEmail("blabla@gmail.com")
        }
    }

    @Test
    fun `delete user with success`() {
        whenever(userRepository.findById(1)).thenReturn(buildOptionalUser())
        userService.delete(1)
    }

    @Test
    fun `delete user when user id does not exist`() {
        whenever(userRepository.findById(0)).thenReturn(Optional.empty())
        assertThrows(NotFoundException::class.java) {
            userService.delete(0)
        }
    }

    @Test
    fun `update user with success`() {
        val optionalUser = buildOptionalUser()
        val user = optionalUser.get()

        val userRequest = UserUpdateRequest(name = user.name, cpfOrCnpj = user.cpfOrCnpj, userName = optionalUser.get().userName,
                birthDate = user.birthDate, loggedByFace = user.loggedByFace, password = user.password, photo = user.photo)
        whenever(userRepository.findById(1)).thenReturn(optionalUser)
        whenever(userRepository.save(user)).thenReturn(buildUser())
        val response = userService.update(1, userRequest)
        TestCase.assertNotNull(response)
    }

    @Test
    fun `update user when user id does not exist`() {
        assertThrows(NotFoundException::class.java) {
            userService.update(0, UserUpdateRequest())
        }
    }

    @Test
    fun `insert user with success`() {
        val user = User(id = 0, name = "insert test", email = "insert_user@gmail.com",
                cpfOrCnpj = "012.568.498-00", passwords = "123", userName = "itest", profiles = setOf("CLIENT"))
        val userRequest = UserRequest(name = "insert test", email = "insert_user@gmail.com",
                cpfOrCnpj = "012.568.498-00", password = "123", userName = "itest")
        whenever(userRepository.save(user)).thenReturn(buildUser())
        userService.insert(userRequest)
    }

}
