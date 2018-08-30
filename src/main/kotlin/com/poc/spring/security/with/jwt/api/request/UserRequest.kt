package com.poc.spring.security.with.jwt.api.request

import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
data class UserRequest(
    @field:NotBlank(message = "Name can not be empty!") val name: String = "",
    @field:[NotBlank(message = "Email not be empty!") Email] val email: String = "",
    @field:[NotBlank(message = "Password not be empty")
    Min(value = 4, message = "Password must contain at least 3 characters!")] val password: String = "",
    val birthDate: String? = null,
    val photo: String? = null,
    val userName: String = email,
    val cpfOrCnpj: String ? = null,
    val loggedByFace: Boolean = false
)
