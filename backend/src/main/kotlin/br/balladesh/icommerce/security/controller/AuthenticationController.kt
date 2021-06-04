package br.balladesh.icommerce.security.controller

import br.balladesh.icommerce.security.TokenHelper
import br.balladesh.icommerce.security.dto.*
import br.balladesh.icommerce.security.entity.User
import br.balladesh.icommerce.security.entity.UserRoleName
import br.balladesh.icommerce.security.repository.AuthorityRepository
import br.balladesh.icommerce.security.repository.UserRepository
import br.balladesh.icommerce.security.service.CustomUserDetailsService
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import kotlin.collections.HashMap

@RestController
@RequestMapping(value = ["/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthenticationController(
  var tokenHelper: TokenHelper,
  @Lazy private val authenticationManager: AuthenticationManager,
  private val userDetailsService: CustomUserDetailsService,
  private val userRepository: UserRepository,
  private val authorityRepository: AuthorityRepository,
)
{
  @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
  @Throws(AuthenticationException::class,IOException::class)
  fun createAuthenticationTokenWhenLogin(@RequestBody authenticationRequest: LoginAuthenticationRequest): ResponseEntity<JwtTokenResponse> {
    val authentication = authenticationManager.authenticate(
      UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
    )

    SecurityContextHolder.getContext().authentication = authentication

    val user = authentication.principal as User
    val jwts = tokenHelper.generateToken(user.username)
    val expiresIn = tokenHelper.getExpiredTime()

    return ResponseEntity.ok(JwtTokenResponse(jwts, expiresIn.toLong()))
  }

  @RequestMapping(value = ["/signup"], method = [RequestMethod.POST])
  fun userSignUp(
    @RequestBody request: AccountSignUpRequest
  ): ResponseEntity<MessageResponse> {
    // Search for a user with this username
    val searchedUser = userRepository.findByUsername(request.username)

    if (searchedUser.isPresent) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(
          MessageResponse(HttpStatus.CONFLICT.value(), "O usuário informado já existe")
        )
    }

    return try {
      val authority = authorityRepository.findByName(UserRoleName.ROLE_USER)

      val hashedPassword = userDetailsService.hashPassword(request.password)
      val user = User( request.name, request.username, hashedPassword, mutableSetOf(authority) )

      userRepository.save(user)

      ResponseEntity
        .ok(
          MessageResponse(HttpStatus.OK.value(), "A conta foi criada com sucesso")
        )
    } catch (e: Exception) {
      ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
          MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro ao criar o usuário")
        )
    }
  }

  @RequestMapping(value = ["/refresh"], method = [RequestMethod.POST])
  fun createAuthenticationTokenWhenRefreshing(request: HttpServletRequest): ResponseEntity<JwtTokenResponse> {
    val authToken = tokenHelper.getToken(request)

    if (!authToken.isPresent) {
      return ResponseEntity.accepted().body(JwtTokenResponse())
    }

    val refreshedToken = tokenHelper.refreshToken(authToken.get()).orElse("")
    val expiresIn = tokenHelper.getExpiredTime()

    return ResponseEntity.ok(JwtTokenResponse(refreshedToken, expiresIn.toLong()))
  }

  @RequestMapping(value = ["/change-password"], method = [RequestMethod.POST])
  @PreAuthorize("hasRole('USER')")
  fun changePassword(@RequestBody passwordChangerRequest: PasswordChangerRequest): ResponseEntity<Any> {
    userDetailsService.changePassword(passwordChangerRequest.oldPassword, passwordChangerRequest.newPassword)
    val result = HashMap<String, String>()
    result.put("result", "The password was changed successfully!")
    return ResponseEntity.accepted().body(result)
  }
}
