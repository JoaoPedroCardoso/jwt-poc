package com.poc.spring.security.with.jwt.reposiroty

import com.poc.spring.security.with.jwt.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */
interface UserRepository : JpaRepository<User, Int>{

    @Transactional(readOnly = true)
    fun findByUserName(userName: String): User?

    @Transactional(readOnly = true)
    fun findByEmail(email: String): User?

}
