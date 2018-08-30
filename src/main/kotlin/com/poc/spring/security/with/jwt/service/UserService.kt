package com.poc.spring.security.with.jwt.service

import com.poc.spring.security.with.jwt.api.request.UserRequest
import com.poc.spring.security.with.jwt.api.request.UserUpdateRequest
import com.poc.spring.security.with.jwt.controller.converter.toModel
import com.poc.spring.security.with.jwt.infrastruct.utils.MessageUtils
import com.poc.spring.security.with.jwt.model.User
import com.poc.spring.security.with.jwt.reposiroty.UserRepository
import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val messageSource: MessageSource
) {

    fun findAll(): List<User> =
        userRepository.findAll().toList()

    fun findById(id: Int): User =
        userRepository.findById(id).orElseThrow { throw NotFoundException(
            this.messageSource.getMessage(
                MessageUtils.OBJECT_NOT_FOUND_ID, arrayOf(id),
                MessageUtils.DEFAULT_LOCALE
            )) }

    fun findByUserName(userName: String): User =
        userRepository.findByUserName(userName) ?: throw NotFoundException(
            this.messageSource.getMessage(
                MessageUtils.USER_NAME_NOT_FOUND, arrayOf(userName),
                MessageUtils.DEFAULT_LOCALE
            ))

    fun findByEmail(email: String): User =
        userRepository.findByEmail(email) ?: throw NotFoundException(
            this.messageSource.getMessage(
                MessageUtils.EMAIL_NOT_FOUND, arrayOf(email),
                MessageUtils.DEFAULT_LOCALE
            ))

    fun insert(request: UserRequest): User =
        userRepository.save(request.toModel())

    fun update(id: Int, request: UserUpdateRequest): User {
        var user = userRepository.findById(id).orElseThrow { throw NotFoundException(
            this.messageSource.getMessage(
                MessageUtils.OBJECT_NOT_FOUND_ID, arrayOf(id),
                MessageUtils.DEFAULT_LOCALE
            )) }
        user = user.copy(
            name = request.name.takeIf { it.isNotBlank() } ?: user.name,
            password = request.password.takeIf { it.isNotBlank() } ?: user.password,
            userName = request.userName.takeIf { !it.isNullOrBlank() && it!!.isNotBlank() } ?: user.userName,
            loggedByFace = request.loggedByFace.takeIf { it != user.loggedByFace} ?: user.loggedByFace,
            photo = request.photo.takeIf { !it.isNullOrBlank() && it!!.isNotBlank() } ?: user.photo,
            cpfOrCnpj = request.cpfOrCnpj.takeIf { !it.isNullOrBlank() && it!!.isNotBlank() } ?: user.cpfOrCnpj,
            birthDate = request.birthDate.takeIf {  !it.isNullOrBlank() && it!!.isNotBlank()} ?: user.birthDate
        )
        return userRepository.save(user)
    }

    fun delete(id: Int) {
        val user = userRepository.findById(id).orElseThrow { throw NotFoundException(
            this.messageSource.getMessage(
                MessageUtils.OBJECT_NOT_FOUND_ID, arrayOf(id),
                MessageUtils.DEFAULT_LOCALE
            )) }

            userRepository.deleteById(id)
    }


}
