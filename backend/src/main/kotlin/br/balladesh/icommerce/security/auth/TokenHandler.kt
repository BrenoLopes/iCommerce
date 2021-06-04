package br.balladesh.icommerce.security.auth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter
import br.balladesh.icommerce.security.TokenHelper
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter(
  private val tokenHelper: TokenHelper,
  private val userDetailsService: UserDetailsService
) : OncePerRequestFilter()
{
  override fun doFilterInternal(request: HttpServletRequest,response: HttpServletResponse,filterChain: FilterChain){
    val authToken = tokenHelper.getToken(request)

    if (authToken.isPresent) {
      processToken(authToken.get())
    }

    filterChain.doFilter(request, response)
  }

  private fun processToken(authToken: String) {
    val username = tokenHelper.getUsernameFromToken(authToken)
    if (!username.isPresent) return

    val userDetails = userDetailsService.loadUserByUsername(username.get())
    if (!tokenHelper.validateToken(authToken, userDetails)) return

    val authentication = TokenBasedAuthentication(authToken, userDetails)
    SecurityContextHolder.getContext().authentication = authentication
  }
}

data class TokenBasedAuthentication(
  private var token: String,
  private val userDetails: UserDetails
): AbstractAuthenticationToken(userDetails.authorities)
{
  override fun isAuthenticated() = true
  override fun getCredentials() = token
  override fun getPrincipal() = userDetails
}
