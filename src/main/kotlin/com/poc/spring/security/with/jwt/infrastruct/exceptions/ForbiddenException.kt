package com.poc.spring.security.with.jwt.infrastruct.exceptions

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
class ForbiddenException : RuntimeException {

    constructor(msg: String) : super(msg)

    constructor(msg: String, cause: Throwable) : super(msg, cause)

    companion object {
        private val serialVersionUID = 1L
    }

}
