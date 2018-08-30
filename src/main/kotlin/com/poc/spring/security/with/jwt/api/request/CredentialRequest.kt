package com.poc.spring.security.with.jwt.api.request

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
data class CredentialRequest constructor(val username: String = "",
                                         val password: String = "")
