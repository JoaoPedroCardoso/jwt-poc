package com.poc.spring.security.with.jwt.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.stream.Collectors
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * Created by JoaoPedroCardoso on 30/08/18
 */

@Entity
data class User @JvmOverloads constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int = 0,
    @field:NotBlank val name: String = "",
    @field:[NotBlank Email] @Column(unique = true) val email: String = "",
    @field:NotBlank @JsonIgnore val passwords: String = "",
    val birthDate: String? = null,
    @JsonIgnore val photo: String? = null,
    val userName: String = email,
    val cpfOrCnpj: String? = null,
    val loggedByFace: Boolean = false,
    @JsonIgnore @ElementCollection(fetch = FetchType.EAGER) @CollectionTable(name = "PROFILES") val profiles:
    Set<String> =
        emptySet()
) : Serializable, UserDetails{

    fun getProfile() =
        profiles.map({ x -> UserProfile.fromString(x).value })

    fun addProfiles(profiles: UserProfile) =
        this.profiles.plus(profiles)

    override fun getPassword() = passwords

    override fun getAuthorities(): Collection<GrantedAuthority> =
        this.profiles.stream().map({ x -> SimpleGrantedAuthority(x.toString()) })
            .collect(Collectors.toList<SimpleGrantedAuthority>())

    override fun getUsername() = userName

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

}
