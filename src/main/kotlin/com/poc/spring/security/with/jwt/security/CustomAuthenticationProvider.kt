package com.poc.spring.security.with.jwt.security

import com.poc.spring.security.with.jwt.infrastruct.exceptions.UnauthorizedException
import com.poc.spring.security.with.jwt.infrastruct.utils.MessageUtils
import com.poc.spring.security.with.jwt.model.User
import com.poc.spring.security.with.jwt.service.UserDetailsService
import org.springframework.context.MessageSource
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.ArrayList

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@Component
class CustomAuthenticationProvider(private val userDetailsService: UserDetailsService,
                                   private val messageSource: MessageSource
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val user = userDetailsService.loadUserByUsername(authentication.name)
        val password = authentication.credentials.toString()
        val authenticationResponse = UsernamePasswordAuthenticationToken(user, password, ArrayList<GrantedAuthority>())

        return when(authentication.isAuthenticated &&  shouldAuthenticateAgainstThirdPartySystem(user = user,
            password = password)) {
            true -> authenticationResponse
            false -> throw UnauthorizedException(
                this.messageSource.getMessage(
                MessageUtils.INVALID_CREDENTIAL, null,
                MessageUtils.DEFAULT_LOCALE
            ))
        }
    }

    override fun supports(authentication: Class<*>) =
        authentication == UsernamePasswordAuthenticationToken::class.java

    private fun shouldAuthenticateAgainstThirdPartySystem(user: User, password: String): Boolean = user.password == password

}
