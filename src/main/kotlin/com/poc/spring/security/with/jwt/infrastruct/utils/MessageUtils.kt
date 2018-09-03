package com.poc.spring.security.with.jwt.infrastruct.utils

import org.springframework.context.i18n.LocaleContextHolder
import java.util.Locale

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
open class MessageUtils {

    companion object {

        /**
         * Default locale
         */
        val DEFAULT_LOCALE = Locale(
            LocaleContextHolder.getLocale().language,
            LocaleContextHolder.getLocale().country
        )

        /**
         * Default config
         */
        val BASE_NAME = "messages"
        val DEFAULT_ENCODING = "UTF-8"

        /**
         * Messages generic
         */
        val OBJECT_NOT_FOUND_ID = "object.not.found.id"

        /**
         * Messages of User
         */
        val EMAIL_NOT_FOUND = "email.not.found"

        val USER_NAME_NOT_FOUND = "user.name.not.found"

        /**
         * Messages of Login
         */
        val USER_NAME_OR_EMAIL_NOT_FOUND = "user.email.not.found"
        val INVALID_TOKEN = "invalid.token"
        val INVALID_CREDENTIAL = "invalid.credential"
        val INVALID_PARSE_CREDENTIAL_REQUEST = "invalid.parse.credential.request"

        /**
         * Configuration of variable languages
         */
        val ENGLISH = "en"
        val US = "US"
        val PORTUGUESE = "pt"
        val BR = "BR"

        val US_LOCALE = Locale(ENGLISH, US)

        val PT_LOCALE = Locale(PORTUGUESE, BR)
    }

}
