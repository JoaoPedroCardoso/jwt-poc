package com.poc.spring.security.with.jwt.service.db

import com.poc.spring.security.with.jwt.model.User
import com.poc.spring.security.with.jwt.reposiroty.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.ParseException
import java.util.Arrays
import java.util.UUID

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@Service
class DatabaseService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(ParseException::class)
    fun instantiateTestDataBase() {

        val usu1 = User(
            name = "Leonardo Siqueira", cpfOrCnpj = "123456789", userName = "leocm", passwords = "123",
            email = "leo.siqueira@gmail.com", birthDate = "10/10/1990", loggedByFace = true, photo = "${UUID
                .randomUUID()}", profiles = hashSetOf(1)
        )
        val usu2 = User(
            name = "Tester", cpfOrCnpj = "1010101034", userName = "tester@hotmail.com", passwords = "123",
            email = "tester@hotmail.com", birthDate = "10/10/1980", profiles = hashSetOf(1)
        )
        val usu3 = User(
            name = "Victor Gomes", cpfOrCnpj = "1010101034", userName = "vict", passwords = "123",
            email = "vito.gomes@gmail.com", birthDate = "10/10/2000", profiles = hashSetOf(2)
        )
        val usu4 = User(
            name = "Murillo Ferreira",
            cpfOrCnpj = "1020304034",
            userName = "murillo.ferreira@gmail.com",
            passwords = "123",
            email = "murillo.ferreira@gmail.com",
            birthDate = "10/10/1975",
            profiles = hashSetOf(1)
        )
        val usu5 = User(
            name = "Zezin da Silva", cpfOrCnpj = "102030009", userName = "zezin.silva@gmail.com", passwords = "123",
            email = "zezin.silva@gmail.com", birthDate = "10/10/1960", profiles = hashSetOf(1)
        )

        userRepository.saveAll(Arrays.asList(usu1, usu2, usu3, usu4,usu5))

    }

}
