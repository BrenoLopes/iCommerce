package br.balladesh.icommerce.security.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import javax.persistence.*

enum class UserRoleName {
  ROLE_USER, ROLE_ADMIN
}

@Entity
@Table(name = "users")
class User() : UserDetails, Serializable {
  @Column(name = "name")
  var name: String = ""

  @Column(name = "username")
  private var username: String = ""

  @JsonIgnore
  @Column(name = "password")
  private var password: String = ""

  @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @JoinTable(
    name = "user_authority",
    joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
    inverseJoinColumns = [JoinColumn(name = "authority_id", referencedColumnName = "id")],
  )
  private var authorities: MutableList<Authority> = mutableListOf()

  @Column(name = "enabled")
  private var enabled: Boolean = true

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  constructor(name: String, username: String, password: String, authorities: MutableList<Authority>, id: Long): this() {
    this.name = name
    this.username = username
    this.password = password
    this.authorities = authorities
    this.id = id
  }

  constructor(name: String, username: String, password: String, authorities: MutableList<Authority>)
    : this(name, username, password, authorities, -1)

  override fun getUsername(): String = username
  fun setUsername(username: String) { this.username = username }

  override fun isEnabled(): Boolean = enabled
  fun setEnabled(enabled: Boolean) { this.enabled = enabled }

  override fun getAuthorities(): Collection<GrantedAuthority> = authorities
  fun setAuthorities(authorities: MutableList<Authority>) { this.authorities = authorities }

  override fun getPassword(): String = password
  fun setPassword(password: String) { this.password = password }

  @JsonIgnore override fun isAccountNonExpired() = true
  @JsonIgnore override fun isAccountNonLocked() = true
  @JsonIgnore override fun isCredentialsNonExpired() = true

  companion object {
    const val serialVersionUID = 7223323436867575807L
  }
}
