package br.balladesh.icommerce.security.dto

data class JwtTokenResponse(
  val access_token: String = "",
  val expires_in: Long = -1
)

data class MessageResponse(
  val status: Int,
  val message: String
)
