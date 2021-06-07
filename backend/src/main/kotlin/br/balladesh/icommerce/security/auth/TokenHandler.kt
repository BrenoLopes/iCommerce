package br.balladesh.icommerce.security.auth

import br.balladesh.icommerce.security.TokenHelper
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.*
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

@Component
class CorsFilter : Filter {
  @Throws(IOException::class, ServletException::class)
  override fun doFilter(req: ServletRequest?, res: ServletResponse, chain: FilterChain) {
    val response = res as HttpServletResponse
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE")
    response.setHeader("Access-Control-Max-Age", "3600")
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With")
    chain.doFilter(req, res)
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
