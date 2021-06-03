package br.balladesh.icommerce.security.dto

data class LoginAuthenticationRequest(
  val username: String,
  val password: String,
)

data class PasswordChangerRequest(
  val oldPassword: String,
  val newPassword: String,
)

data class AccountSignUpRequest(
  val name: String,
  val username: String,
  val password: String,
)
