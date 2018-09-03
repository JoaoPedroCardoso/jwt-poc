package com.poc.spring.security.with.jwt.config

import com.poc.spring.security.with.jwt.security.CustomAuthenticationProvider
import com.poc.spring.security.with.jwt.security.JWTAuthenticationFilter
import com.poc.spring.security.with.jwt.security.JWTAuthorizationFilter
import com.poc.spring.security.with.jwt.security.JWTUtil
import com.poc.spring.security.with.jwt.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private val userDetailsService: UserDetailsService? = null

    @Autowired
    private val jwtUtil: JWTUtil? = null

    @Autowired
    private val authProvider: CustomAuthenticationProvider? = null

    companion object {

        private val PUBLIC_MATCHERS = arrayOf("/**")

        private val PUBLIC_MATCHERS_GET = arrayOf("/**")

        private val PUBLIC_MATCHERS_POST = arrayOf("/**")

        private val PUBLIC_MATCHERS_DELETE = arrayOf("/**")

        private val PUBLIC_MATCHERS_PUT = arrayOf("/**")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.csrf().disable()
        http.cors().configurationSource(corsConfigurationSource())
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, *PUBLIC_MATCHERS_GET).permitAll()
            .anyRequest().authenticated().and()
            .addFilterBefore(JWTAuthenticationFilter(authenticationManager(), jwtUtil!!), UsernamePasswordAuthenticationFilter::class.java)
            .addFilter(JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService!!))

    }

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(authProvider)
        auth.userDetailsService<org.springframework.security.core.userdetails.UserDetailsService>(userDetailsService)
    }

    @Bean
    internal fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

    @Bean
    internal fun corsConfiguration(): CorsConfiguration {
        val cors = CorsConfiguration()
        cors.addAllowedOrigin("*")
        cors.addAllowedMethod("*")
        cors.addAllowedHeader("*")
        cors.setAllowCredentials(true)
        cors.setMaxAge(1800L)
        return cors
    }

}
