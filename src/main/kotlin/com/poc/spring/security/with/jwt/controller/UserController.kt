package com.poc.spring.security.with.jwt.controller

import com.poc.spring.security.with.jwt.api.UserApi
import com.poc.spring.security.with.jwt.api.request.UserRequest
import com.poc.spring.security.with.jwt.api.request.UserUpdateRequest
import com.poc.spring.security.with.jwt.model.User
import com.poc.spring.security.with.jwt.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@RestController
class UserController @Autowired constructor(private val userService: UserService) : UserApi {

    override fun findById(@PathVariable(value = "id") id: Int): ResponseEntity<User> =
        ResponseEntity.ok().body(userService.findById(id))

    override fun findByUserName(@PathVariable(value = "userName") userName: String): ResponseEntity<User> =
        ResponseEntity.ok().body(userService.findByUserName(userName = userName))

    override fun findAll(): ResponseEntity<List<User>> =
        ResponseEntity.ok().body(userService.findAll())

    override fun insert(@RequestBody @Valid request: UserRequest): ResponseEntity<User> =
        ResponseEntity.ok().body(userService.insert(request))

    override fun update(@PathVariable(value = "id") id: Int, @RequestBody request: UserUpdateRequest): ResponseEntity<User> =
        ResponseEntity.ok().body(userService.update(id, request))

    override fun delete(@PathVariable(value = "id") id: Int): ResponseEntity<Any> =
        ResponseEntity.ok().body(userService.delete(id))

}
