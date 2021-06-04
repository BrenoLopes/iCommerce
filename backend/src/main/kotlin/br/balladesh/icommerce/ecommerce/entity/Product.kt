package br.balladesh.icommerce.ecommerce.entity

import br.balladesh.icommerce.security.entity.User
import org.hibernate.annotations.JoinColumnOrFormula
import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "product")
class Product(): Serializable {
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  @Column(name = "name")
  var name: String = ""

  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  var category: Category = Category()

  @Column(name = "description")
  var description: String = ""

  @Column(name = "price", scale = 15, precision = 4)
  var price: BigDecimal = BigDecimal(0.0)

  @ManyToOne
  @JoinColumn(name = "client_id", referencedColumnName = "id")
  var user: User = User()

  constructor(id: Long, name: String, category: Category, description: String): this() {
    this.id = id
    this.name = name
    this.category = category
    this.description = description
  }

  constructor(name: String, category: Category, description: String): this(-1, name, category, description)

  companion object {
    const val serialVersionUID = 323356734254775807L
  }
}
