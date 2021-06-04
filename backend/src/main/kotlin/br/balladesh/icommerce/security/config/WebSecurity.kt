package br.balladesh.icommerce.security.config

import br.balladesh.icommerce.security.TokenHelper
import br.balladesh.icommerce.security.auth.RestAuthenticationEntryPoint
import br.balladesh.icommerce.security.auth.TokenAuthenticationFilter
import br.balladesh.icommerce.security.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
  private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

  @Autowired
  private var jwtUserDetailsService: CustomUserDetailsService? = null

  @Bean
  fun passwordEncoder(): PasswordEncoder? {
    return BCryptPasswordEncoder()
  }

  @Bean
  @Throws(Exception::class)
  override fun authenticationManagerBean(): AuthenticationManager? {
    return super.authenticationManagerBean()
  }

  @Autowired
  @Throws(Exception::class)
  fun configureGlobal(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(jwtUserDetailsService)
      .passwordEncoder(passwordEncoder())
  }

  @Autowired
  var tokenHelper: TokenHelper? = null

  @Throws(Exception::class)
  override fun configure(http: HttpSecurity) {
    http
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
      .authorizeRequests()
      .antMatchers(
        HttpMethod.GET,
        "/",
        "/auth/**",
        "/webjars/**",
        "/*.html",
        "/favicon.ico",
        "/**/*.html",
        "/**/*.css",
        "/**/*.js"
      ).permitAll()
      .antMatchers("/auth/**").permitAll()
      .anyRequest().authenticated().and()
      .addFilterBefore(
        TokenAuthenticationFilter(tokenHelper!!, jwtUserDetailsService!!),
        BasicAuthenticationFilter::class.java
      )
    http.csrf().disable()
  }


  @Throws(Exception::class)
  override fun configure(web: WebSecurity) {
    // TokenAuthenticationFilter will ignore the below paths
    web.ignoring().antMatchers(
      HttpMethod.POST,
      "/auth/login",
      "/auth/signup",
      "/auth/refresh"
    )
    web.ignoring().antMatchers(
      HttpMethod.GET,
      "/",
      "/webjars/**",
      "/*.html",
      "/favicon.ico",
      "/**/*.html",
      "/**/*.css",
      "/**/*.js"
    )
  }
}
