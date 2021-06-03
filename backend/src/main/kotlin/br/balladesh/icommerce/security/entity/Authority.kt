package br.balladesh.icommerce.security.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name="authority")
class Authority() : GrantedAuthority, Serializable {
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

  constructor(name: UserRoleName) : this(name, -1)

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

  companion object {
    const val serialVersionUID = 2221434536867876807L
  }
}

