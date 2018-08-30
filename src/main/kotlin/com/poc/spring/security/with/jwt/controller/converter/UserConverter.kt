package com.poc.spring.security.with.jwt.controller.converter

import com.poc.spring.security.with.jwt.api.request.UserRequest
import com.poc.spring.security.with.jwt.model.User

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */

fun UserRequest.toModel() = User(
    name = name, cpfOrCnpj = cpfOrCnpj, userName = userName, password = password,
    email = email, loggedByFace = loggedByFace, photo = photo, birthDate = birthDate
)
