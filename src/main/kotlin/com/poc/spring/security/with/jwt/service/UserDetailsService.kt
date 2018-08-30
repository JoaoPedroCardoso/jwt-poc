package com.poc.spring.security.with.jwt.service

import com.poc.spring.security.with.jwt.model.User
import com.poc.spring.security.with.jwt.reposiroty.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
@Service
class UserDetailsService @Autowired
constructor(private val userRepository: UserRepository) :
    UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String): User =
        userRepository.findByUserName(userName) ?: throw UsernameNotFoundException(userName)

}
