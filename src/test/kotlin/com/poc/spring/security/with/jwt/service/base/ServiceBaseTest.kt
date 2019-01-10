package com.poc.spring.security.with.jwt.service.base

import com.poc.spring.security.with.jwt.ApplicationTests
import com.poc.spring.security.with.jwt.model.User
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@SpringBootTest
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [ServiceTestConfig::class, ApplicationTests::class])
abstract class ServiceBaseTest {

    fun buildUserListResponse(): List<User> =
            Arrays.asList(
                    User(
                            id = 1, name = "User Test", cpfOrCnpj = "037-188-983-11", userName = "user", passwords =
                    "123", email = "teste@gmail.com", birthDate = "22/05/1998", loggedByFace = false
                    )
            )

    fun buildOptionalUser() = Optional.of(User(
            id = 1, name = "User Test", cpfOrCnpj = "037-188-983-11", userName = "user", passwords =
    "123", email = "teste@gmail.com", birthDate = "22/05/1998", loggedByFace = false
    ))

    fun buildUser() = User(
            id = 1, name = "User Test", cpfOrCnpj = "037-188-983-11", userName = "vict", passwords =
    "123", email = "vito.gomes@gmail.com", birthDate = "22/05/1998", loggedByFace = false
    )
}