package com.poc.spring.security.with.jwt.api.request

import javax.validation.constraints.NotBlank

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
data class CredentialRequest constructor(@field: NotBlank(message = "username can not be empty!") val username: String = "",
                                         @field: NotBlank(message = "password can not be empty!") val password: String = "")
