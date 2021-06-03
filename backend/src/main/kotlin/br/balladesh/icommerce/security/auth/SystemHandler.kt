package br.balladesh.icommerce.security.auth

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
  override fun commence(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
    // This is invoked when the user tries to access a protected resource without credentials
    // As this is a stateless rest server, there is no login page to redirect the user to
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.message)
  }
}
