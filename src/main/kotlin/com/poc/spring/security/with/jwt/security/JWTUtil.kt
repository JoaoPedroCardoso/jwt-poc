package com.poc.spring.security.with.jwt.security

import com.poc.spring.security.with.jwt.infrastruct.exceptions.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.text.ParseException
import java.util.Date

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@Component
class JWTUtil {

    @Value("\${jwt.secret}")
    private val secret: String = ""

    @Value("\${jwt.expiration}")
    private val expiration: Long = 0L

    fun generateToken(username: String): String =
        Jwts.builder().setSubject(username).setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray()).compact()

    fun tokenIsValid(token: String): Boolean {
        val claims = getClaims(token)
        when (claims != null) {
            true -> {
                val username = claims!!.subject
                val expirationDate = claims.expiration
                val now = Date(System.currentTimeMillis())
                if (username.isNotBlank() && expirationDate != null && now.before(expirationDate)) {
                    return true
                }
            }

            false -> return false
        }
        return false
    }

    fun getUsername(token: String): String =
        getClaims(token)?.subject.orEmpty()

    private fun getClaims(token: String): Claims? {
        try {
            return Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
        } catch (e: Exception) {
            throw throw InvalidTokenException("Invalid token")
        }
    }

}
