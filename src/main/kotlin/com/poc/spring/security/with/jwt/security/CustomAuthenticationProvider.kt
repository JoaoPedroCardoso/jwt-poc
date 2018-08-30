package com.poc.spring.security.with.jwt.security

import com.poc.spring.security.with.jwt.service.UserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.util.ArrayList
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component


/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@Component
class CustomAuthenticationProvider(private val userDetailsService: UserDetailsService) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {

        val password = authentication.credentials.toString()

        return if (shouldAuthenticateAgainstThirdPartySystem()) {

            // use the credentials
            // and authenticate against the third-party system
            val user = userDetailsService.loadUserByUsername(authentication.name)
            UsernamePasswordAuthenticationToken(
                user, password, ArrayList<GrantedAuthority>()
            )
        } else {
            null
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }

    private fun shouldAuthenticateAgainstThirdPartySystem() =
            true
}
