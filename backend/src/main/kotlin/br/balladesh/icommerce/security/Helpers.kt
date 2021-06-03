package br.balladesh.icommerce.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import br.balladesh.icommerce.security.entity.User
import io.jsonwebtoken.ExpiredJwtException
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class TokenHelper(
  @Value("\${app.name}")
  private val APP_NAME: String,

  @Value("\${jwt.secret}")
  private val SECRET: String,

  @Value("\${jwt.expires_in}")
  private val EXPIRES_IN: Int,

  @Value("\${jwt.header}")
  private val AUTH_HEADER: String,
) {
  private val signatureAlgorithm = SignatureAlgorithm.HS512

  fun getUsernameFromToken(token: String): Optional<String> {
    val claims = getAllClaimsFromToken(token)
    if (!claims.isPresent) return Optional.empty()

    return Optional.ofNullable(claims.get().subject)
  }

  fun getIssuedAtDateFromToken(token: String): Optional<Date> {
    val claims = getAllClaimsFromToken(token)
    if (!claims.isPresent) return Optional.empty()

    return Optional.ofNullable(claims.get().issuedAt)
  }

  fun refreshToken(token: String): Optional<String> {
    try {
      val claims = this.getAllClaimsFromToken(token)
      if (!claims.isPresent) return Optional.empty()

      claims.get().issuedAt = Date()

      return Optional.ofNullable(Jwts.builder()
        .setClaims(claims.get())
        .setExpiration(generateExpirationDate())
        .signWith(signatureAlgorithm, SECRET)
        .compact()
      )
    } catch (e: Exception) {
      return Optional.empty()
    }
  }

  fun generateToken(username: String): String {
    return Jwts.builder()
      .setIssuer(APP_NAME)
      .setSubject(username)
      .setAudience("iCommerceWebApp")
      .setIssuedAt(Date())
      .setExpiration(generateExpirationDate())
      .signWith(signatureAlgorithm, SECRET)
      .compact()
  }

  fun getExpiredTime(): Int {
    return EXPIRES_IN
  }

  fun validateToken(token: String, userDetails: UserDetails): Boolean {
    val user = userDetails as User

    val username = getUsernameFromToken(token)
    val createdAt = getIssuedAtDateFromToken(token)

    return username.isPresent
      && createdAt.isPresent
      && username.get() == userDetails.username
  }

  fun getToken(request: HttpServletRequest): Optional<String> {
    val authHeader = Optional.ofNullable(request.getHeader(AUTH_HEADER))

    if (authHeader.isPresent && authHeader.get().startsWith("Bearer "))
      return Optional.of(authHeader.get().substring(7))

    return Optional.empty()
  }

  private fun getAllClaimsFromToken(token: String): Optional<Claims> {
    return try {
      Optional.of(
        Jwts.parser()
          .setSigningKey(SECRET)
          .parseClaimsJws(token)
          .body
      )
    } catch (e: ExpiredJwtException) {
      Optional.of(e.claims)
    } catch (e: Exception) {
      Optional.empty()
    }
  }

  private fun generateExpirationDate(): Date {
    return Date(Date().time + EXPIRES_IN * 1000)
  }

  private fun isCreatedBeforeLastPasswordReset(createdAt: Date, lastPasswordResetDate: Date): Boolean {
    return createdAt.before(lastPasswordResetDate)
  }
}
