package com.poc.spring.security.with.jwt.config

import com.poc.spring.security.with.jwt.service.db.DatabaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.text.ParseException

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@Configuration
@Profile("test")
class TestConfig {

    @Autowired
    private val dbtest: DatabaseService? = null

    @Value("\${spring.jpa.hibernate.ddl-auto}")
    private val strategy: String? = null

    @Bean
    @Throws(ParseException::class)
    fun instantiateDataBase(): Boolean? {

        if (!"create".equals(strategy!!, ignoreCase = true)) {
            return false
        }

        dbtest!!.instantiateTestDataBase()
        return true
    }

}
