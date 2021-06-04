package br.balladesh.icommerce.security.dto

import org.springframework.http.HttpStatus

data class JwtTokenResponse(
  val access_token: String = "",
  val expires_in: Long = -1
)

data class MessageResponse(
  val status: Int,
  val message: String
) {
  constructor(status: HttpStatus, message: String): this(status.value(), message)
  constructor(message: String) : this(HttpStatus.OK, message)
}
