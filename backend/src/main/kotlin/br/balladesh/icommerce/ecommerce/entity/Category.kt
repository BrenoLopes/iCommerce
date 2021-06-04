package br.balladesh.icommerce.ecommerce.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "category")
class Category(): Serializable
{
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  @Column(name = "name")
  var name: String = ""

  constructor(id: Long, name: String): this() {
    this.id = id
    this.name = name
  }

  constructor(name: String): this(-1, name)

  companion object {
    const val serialVersionUID = 323356786352345807L
  }
}
