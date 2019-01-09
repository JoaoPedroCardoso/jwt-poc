package com.poc.spring.security.with.jwt.service

import com.poc.spring.security.with.jwt.ApplicationTests
import com.poc.spring.security.with.jwt.api.request.UserRequest
import com.poc.spring.security.with.jwt.api.request.UserUpdateRequest
import javassist.NotFoundException
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class UserServiceTests : ApplicationTests() {

    @Autowired
    lateinit var userService: UserService

    @Test
    fun `find all user with success`() {
        val list = userService.findAll()
        assertTrue(list.isNotEmpty())
    }

    @Test
    fun `find user by id with success`() {
        val user = userService.findById(1)
        assertEquals(1, user.id)
    }

    @Test
    fun `find user by invalid id`() {
        val exception = assertThrows(NotFoundException::class.java) {
            userService.findById(0)
        }
        assertEquals("O objeto com ID 0 não foi encontrado.", exception.message)
    }

    @Test
    fun `find user by userName with success`() {
        val user = userService.findByUserName("vict")
        assertEquals("vict", user.userName)
    }

    @Test
    fun `find user by invalid userName`() {
        val exception = assertThrows(NotFoundException::class.java) {
            userService.findByUserName("blabla")
        }
        assertEquals("Objeto não encontrado: user com o nome - blabla.", exception.message)
    }

    @Test
    fun `find user by email with success`() {
        val user = userService.findByEmail("vito.gomes@gmail.com")
        assertEquals("vito.gomes@gmail.com", user.email)
    }

    @Test
    fun `find user by invalid email`() {
        val exception = assertThrows(NotFoundException::class.java) {
            userService.findByEmail("blabla@gmail.com")
        }
        assertEquals("Objeto não encontrado: user com o email - blabla@gmail.com.", exception.message)
    }

    @Test
    fun `delete user with success`() {
        userService.delete(1)
    }

    @Test
    fun `delete user when user id does not exist`() {
        val exception = assertThrows(NotFoundException::class.java) {
            userService.delete(0)
        }
        assertEquals("O objeto com ID 0 não foi encontrado.", exception.message)
    }

    @Test
    fun `update user with success`() {
        val user = userService.update(1, UserUpdateRequest(name = "userUpdateTest"))
        assertEquals("userUpdateTest", user.name)
    }

    @Test
    fun `update user when user id does not exist`() {
        val exception = assertThrows(NotFoundException::class.java) {
            userService.update(0, UserUpdateRequest())
        }
        assertEquals("O objeto com ID 0 não foi encontrado.", exception.message)
    }

    @Test
    fun `insert user with success`() {
        userService.insert(UserRequest(name = "insert test", email = "insert_user@gmail.com",
                cpfOrCnpj = "012.568.498-00", password = "123", userName = "itest"))
    }

}
