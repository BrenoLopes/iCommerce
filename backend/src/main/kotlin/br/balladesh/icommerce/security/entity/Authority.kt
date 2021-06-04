package br.balladesh.icommerce.security.entity

import br.balladesh.icommerce.calculateHashCode
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name="authority")
class Authority() : GrantedAuthority, Serializable {
  constructor(name: UserRoleName) : this(name, -1)

  @Enumerated( EnumType.STRING )
  @Column(name="name")
  private var name: UserRoleName = UserRoleName.ROLE_USER

  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  constructor(name: UserRoleName, id: Long): this() {
    this.name = name
    this.id = id
  }

  override fun getAuthority(): String {
    return name.name
  }

  fun setName(name: UserRoleName) {
    this.name = name
  }

  @JsonIgnore
  fun getName(): UserRoleName {
    return name
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Authority

    if (this.id != other.id || this.name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    return calculateHashCode(
      this.id, this.name
    )
  }

  companion object {
    const val serialVersionUID = 2221434536867876807L
  }
}

