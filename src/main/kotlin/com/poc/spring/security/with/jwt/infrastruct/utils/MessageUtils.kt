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
         * Messages gerica
         */
        val OBJECT_NOT_FOUND_ID = "object.not.found.id"
        val OBJECT_NOT_WORK = "object.not.work"

        /**
         * Messages of block
         */
        val BLOCK_NOT_FOUND_ID = "block.not.found.id"
        val BLOCK_NOT_FOUND_NAME = "block.not.found.name"
        val SAVE_ADDRESS_NULL = "save.block.with.address.null"
        val IMPOSSIBLE_DELETE_BLOCK_HAS_HISTORY = "block.delete.has.history"

        /**
         * Messages of Address
         */
        val IMPOSSIBLE_SAVE_ADDRESS_WITHOUT_COORDINATE = "address.save.without.coordinate"
        val IMPOSSIBLE_DELETE_ADDRESS_HAS_HISTORY = "address.delete.has.history"

        /**
         * Messages of Coordinate
         */
        val IMPOSSIBLE_DELETE_CORDINATE_HAS_HISTORY = "coordinate.delete.has.history"

        /**
         * Messages of Schedule
         */
        val IMPOSSIBLE_DELETE_SCHEDULE_HAS_HISTORY = "schedule.delete.has.history"

        /**
         * Messages of Streak
         */
        val IMPOSSIBLE_DELETE_STREAK_HAS_HISTORY = "streak.delete.has.history"

        /**
         * Messages of User
         */
        val IMPOSSIBLE_DELETE_USER_HAS_HISTORY = "user.delete.has.history"
        val EMAIL_NOT_FOUND = "email.not.not_found"
        val USER_NAME_OR_EMAIL_NOT_FOUND = "user.email.not.found"
        val USER_NAME_NOT_FOUND = "user.name.not.not_found"

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
