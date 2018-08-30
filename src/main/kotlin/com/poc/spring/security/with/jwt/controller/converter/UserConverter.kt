package com.poc.spring.security.with.jwt.controller.converter

import com.poc.spring.security.with.jwt.api.request.UserRequest
import com.poc.spring.security.with.jwt.model.User
import com.poc.spring.security.with.jwt.model.UserProfile

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */

fun UserRequest.toModel() = User(
    name = name, cpfOrCnpj = cpfOrCnpj, userName = userName, passwords = password,
    email = email, loggedByFace = loggedByFace, photo = photo, birthDate = birthDate,
    profiles = hashSetOf(UserProfile.fromString(profile).cod)
)
