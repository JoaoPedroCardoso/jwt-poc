package com.poc.spring.security.with.jwt.security

import com.poc.spring.security.with.jwt.infrastruct.exceptions.ForbiddenException
import com.poc.spring.security.with.jwt.infrastruct.exceptions.InvalidTokenException
import com.poc.spring.security.with.jwt.infrastruct.exceptions.UnauthorizedException
import com.poc.spring.security.with.jwt.service.UserDetailsService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
class JWTAuthorizationFilter(
    authenticationManager: AuthenticationManager, private val jwtUtil: JWTUtil,
    private val userDetailsService: UserDetailsService
) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = request.getHeader("Authorization").orEmpty()
        if (header.isNotBlank() && header.startsWith("Bearer ")) {
            val auth = getAuthentication(header.substring(7))
            SecurityContextHolder.getContext().authentication = auth
            chain.doFilter(request, response)
        }else{
            chain.doFilter(request, response)
        }
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken =
        when(jwtUtil.tokenIsValid(token)) {
            true -> {
                val username = jwtUtil.getUsername(token)
                val user = userDetailsService.loadUserByUsername(username)
                UsernamePasswordAuthenticationToken(user, null, user.authorities)
            }

            false -> throw InvalidTokenException("Invalid token")
        }

}
