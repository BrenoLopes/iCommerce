package br.balladesh.icommerce.security.service

import br.balladesh.icommerce.security.entity.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import br.balladesh.icommerce.security.repository.UserRepository
import org.springframework.context.annotation.Lazy

interface UserService {
  fun findById(id: Long): User
  fun findByUsername(username: String): User
  fun findAll(): List<User>
}

@Service
class UserServiceImpl(
  val userRepository: UserRepository
) : UserService {

  @Throws(UsernameNotFoundException::class)
  override fun findById(id: Long): User {
    return userRepository
      .findById(id)
      .orElseThrow {
        UsernameNotFoundException(
          String.format("Usuário com o id %d não foi encontrado", id)
        )
      }
  }

  override fun findByUsername(username: String): User {
    return userRepository.findByUsername(username).orElseThrow {
      UsernameNotFoundException(
        String.format("Usuário com o nome %s não foi encontrado", username)
      )
    }
  }

  override fun findAll(): List<User> {
    return userRepository.findAll().iterator().asSequence().toList()
  }
}

@Lazy
@Service
class CustomUserDetailsService(
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder,
  private val authenticationManager: AuthenticationManager? = null,
  private val userServiceImpl: UserServiceImpl
) : UserDetailsService {

  fun hashPassword(password: String): String {
    return passwordEncoder.encode(password)
  }

  fun changePassword(oldPassword: String, newPassword: String) {
    val currentUser = SecurityContextHolder.getContext().authentication
    val username = currentUser.name

    authenticationManager?.authenticate(
      UsernamePasswordAuthenticationToken(username, oldPassword)
    )
    val user = loadUserByUsername(username) as User
    user.password = passwordEncoder.encode(username)

    this.userRepository.save(user)
  }

  override fun loadUserByUsername(username: String): UserDetails {
    return userServiceImpl.findByUsername(username)
  }
}
