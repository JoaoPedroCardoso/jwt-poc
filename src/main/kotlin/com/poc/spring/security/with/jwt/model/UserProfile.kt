package com.poc.spring.security.with.jwt.model

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
enum class UserProfile constructor(val value: String) {

    ROLE_ADMIN("ADMIN"),
    ROLE_CLIENT( "CLIENT");

    companion object {

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
