package br.balladesh.icommerce.security.entity

import br.balladesh.icommerce.calculateHashCode
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import javax.persistence.*

enum class UserRoleName {
  ROLE_USER, ROLE_ADMIN
}

@Entity
@Table(name = "users")
class User() : UserDetails, Serializable {
  constructor(
    name: String,
    username: String,
    password: String,
    authorities: MutableSet<Authority>,
    id: Long
  ) : this() {
    this.name = name
    this.username = username
    this.password = password
    this.authorities = authorities
    this.id = id
  }

  constructor(name: String, username: String, password: String, authorities: MutableSet<Authority>)
    : this(name, username, password, authorities, -1)

  @Id
  @Column(name = "id", unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  @Column(name = "name")
  var name: String = ""

  @Column(name = "username", unique = true, length = 50)
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
  private var authorities: MutableSet<Authority> = mutableSetOf()

  @Column(name = "enabled")
  private var enabled: Boolean = true

  override fun getUsername(): String = username
  fun setUsername(username: String) {
    this.username = username
  }

  override fun isEnabled(): Boolean = enabled
  fun setEnabled(enabled: Boolean) {
    this.enabled = enabled
  }

  override fun getAuthorities() = authorities
  fun setAuthorities(authorities: MutableSet<Authority>) {
    this.authorities = authorities
  }

  override fun getPassword(): String = password
  fun setPassword(password: String) {
    this.password = password
  }

  @JsonIgnore
  override fun isAccountNonExpired() = true

  @JsonIgnore
  override fun isAccountNonLocked() = true

  @JsonIgnore
  override fun isCredentialsNonExpired() = true

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as User

    if (
      this.id != other.id
      || this.name != other.name
      || this.username != other.username
      || this.password != other.password
      || this.enabled != other.enabled
      || this.isAccountNonExpired != other.isAccountNonExpired
      || this.isAccountNonLocked != other.isAccountNonLocked
      || this.isCredentialsNonExpired != other.isCredentialsNonExpired
    ) return false

    return true
  }

  override fun hashCode(): Int {
    return calculateHashCode(
      this.id,
      this.name,
      this.username,
      this.password,
      this.enabled,
      this.isAccountNonExpired,
      this.isAccountNonLocked,
      this.isCredentialsNonExpired
    )
  }

  companion object {
    const val serialVersionUID = 7223323436867575807L
  }
}
