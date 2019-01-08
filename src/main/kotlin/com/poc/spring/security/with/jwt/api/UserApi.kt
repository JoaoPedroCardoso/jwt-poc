package com.poc.spring.security.with.jwt.api

import com.poc.spring.security.with.jwt.api.request.UserRequest
import com.poc.spring.security.with.jwt.api.request.UserUpdateRequest
import com.poc.spring.security.with.jwt.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@RequestMapping
interface UserApi {

    companion object {
        const val USER_BASE_URL = "/api/user"
        const val SEARCH_AND_UPDATE_USER_BY_ID_URL = "$USER_BASE_URL/{id}"
        const val SEARCH_USER_BY_NAME_URL = "$USER_BASE_URL/userName/{userName}"
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping(USER_BASE_URL, produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun findAll(): ResponseEntity<List<User>>

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping(SEARCH_AND_UPDATE_USER_BY_ID_URL, produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun findById(@PathVariable(value = "id") id: Int): ResponseEntity<User>

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping(SEARCH_USER_BY_NAME_URL, produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun findByUserName(@PathVariable(value = "userName") userName: String): ResponseEntity<User>

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(USER_BASE_URL, produces = [(MediaType.APPLICATION_JSON_VALUE)], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun insert(@RequestBody @Valid request: UserRequest): ResponseEntity<User>

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PutMapping(SEARCH_AND_UPDATE_USER_BY_ID_URL, produces = [(MediaType.APPLICATION_JSON_VALUE)], consumes = [(MediaType
        .APPLICATION_JSON_VALUE)])
    fun update(@PathVariable(value = "id") id: Int, @RequestBody request: UserUpdateRequest): ResponseEntity<User>

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DeleteMapping(SEARCH_AND_UPDATE_USER_BY_ID_URL, produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun delete(@PathVariable(value = "id") id: Int): ResponseEntity<Any>

}
