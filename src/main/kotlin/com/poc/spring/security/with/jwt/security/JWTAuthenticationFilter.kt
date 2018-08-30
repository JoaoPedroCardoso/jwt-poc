package com.poc.spring.security.with.jwt.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.poc.spring.security.with.jwt.api.request.CredentialRequest
import com.poc.spring.security.with.jwt.model.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.ArrayList
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
class JWTAuthenticationFilter(private var authenticationManagers: AuthenticationManager,
                              private val jwtUtil: JWTUtil)
    : UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse?): Authentication {

        try {
            val creds = ObjectMapper().readValue(req.inputStream, CredentialRequest::class.java)

            val authToken = UsernamePasswordAuthenticationToken(
                creds.username,
                creds.password, ArrayList<GrantedAuthority>()
            )

            val auth = authenticationManagers.authenticate(authToken)
            println("Return auth: $auth")
            return auth
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    @Throws(IOException::class)
    override fun successfulAuthentication(
        req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain?,
        auth: Authentication
    ) {

        val username = (auth.principal as User).userName
        val name = (auth.principal as User).name
        val cpf = (auth.principal as User).cpfOrCnpj
        val perfil = (auth.principal as User).getProfile()
        val idUser = (auth.principal as User).id
        val token = jwtUtil.generateToken(username)
        res.addHeader("Authorization", "Bearer $token")
        // Infos adicionais
        res.addHeader("IdUsuario", idUser.toString() + "")
        res.addHeader("Nome", name)
        res.addHeader("Cpf_cnpj", cpf)
        res.addHeader("User", username)
        res.addHeader("UserProfile", perfil.toString())
        val out = res.writer
        res.contentType = "Json"

        out.println("{")
        out.println("\"Nome\":\"$name\",")
        out.println("\"Id_Usuario\":\"$idUser\",")
        out.println("\"cpf_cnpj\":\"$cpf\",")
        out.println("\"UserProfile\":\"$perfil\",")
        out.println("\"UserName\":\"$username\"")
        out.println("}")
    }
}
