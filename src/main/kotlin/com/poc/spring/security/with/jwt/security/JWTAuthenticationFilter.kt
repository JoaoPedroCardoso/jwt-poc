package com.poc.spring.security.with.jwt.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.poc.spring.security.with.jwt.api.request.CredentialRequest
import com.poc.spring.security.with.jwt.infrastruct.utils.MessageUtils
import com.poc.spring.security.with.jwt.model.User
import org.springframework.context.MessageSource
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.io.PrintWriter
import java.lang.RuntimeException
import java.util.ArrayList
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
class JWTAuthenticationFilter(private var authenticationManagers: AuthenticationManager,
                              private val jwtUtil: JWTUtil,
                              private val messageSource: MessageSource
) : UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) : Authentication {
        val credentials = ObjectMapper().readValue(request.inputStream, CredentialRequest::class.java) ?: throw
        RuntimeException(this.messageSource.getMessage(
            MessageUtils.INVALID_PARSE_CREDENTIAL_REQUEST, null,
            MessageUtils.DEFAULT_LOCALE
        ))

        val authToken = UsernamePasswordAuthenticationToken(
            credentials.username,
            credentials.password,
            ArrayList<GrantedAuthority>()
        )

        return authenticationManagers.authenticate(authToken)
    }

    @Throws(IOException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain?,
        auth: Authentication
    ) {
        val user = auth.principal as User
        val token = jwtUtil.generateToken(user.username)

        response.addHeader("Authorization", "Bearer $token")
        response.contentType = "Json"

        createAndPrintBodyResponse(response.writer, user)
    }

    private fun createAndPrintBodyResponse(
        writer: PrintWriter,
        user: User
    ) {
        val userName = user.userName
        val name = user.name
        val cpfOrCnpj = user.cpfOrCnpj
        val profile = user.getProfile().toString()
        val id = user.id.toString()

        writer.println("{")
        writer.println("\"Name\":\"$name\",")
        writer.println("\"Id\":\"$id\",")
        writer.println("\"CpfOrCnpj\":\"$cpfOrCnpj\",")
        writer.println("\"UserName\":\"$userName\",")
        writer.println("\"Profile\":\"$profile\"")
        writer.println("}")
    }
}
