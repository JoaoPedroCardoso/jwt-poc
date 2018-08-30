package com.poc.spring.security.with.jwt.infrastruct.custom.error

import org.springframework.http.HttpStatus
import java.util.Arrays

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
class ApiError {

    var status: HttpStatus? = null
    var message: String? = null
    var errors: List<String>? = null

    constructor(status: HttpStatus, message: String, errors: List<String>) : super() {
        this.status = status
        this.message = message
        this.errors = errors
    }

    constructor(status: HttpStatus, message: String, error: String) : super() {
        this.status = status
        this.message = message
        errors = Arrays.asList(error)
    }

}

