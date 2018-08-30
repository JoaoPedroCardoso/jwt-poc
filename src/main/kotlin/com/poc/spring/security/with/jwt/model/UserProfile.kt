package com.poc.spring.security.with.jwt.model

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
enum class UserProfile constructor(val cod: Int, val value: String) {

    ADMIN(1, "ADMIN"),
    CLIENT(2, "CLIENT");


    companion object {

        fun toEnum(cod: Int): UserProfile? =
            UserProfile.values().firstOrNull { it.cod == cod } ?: throw IllegalArgumentException("Id invalido $cod")

        @JsonCreator
        @JvmStatic
        fun fromString (value: String) : UserProfile{
            UserProfile.values().forEach { if(it.value.toUpperCase() == value.toUpperCase() || it.name.toUpperCase() == value.toUpperCase()) return it }
            throw IllegalArgumentException()
        }
    }

    override fun toString(): String {
        return value
    }

}
