package br.balladesh.icommerce.ecommerce.entity

import br.balladesh.icommerce.calculateHashCode
import br.balladesh.icommerce.security.entity.User
import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "product")
class Product() : Serializable
{
  constructor(id: Long, name: String, category: Category, description: String, price: BigDecimal, vendor: User) : this() {
    this.id = id
    this.name = name
    this.setCategoryBidirectionally(category)
    this.description = description
    this.price = price
    this.vendor = vendor
  }

  constructor(name: String, category: Category, description: String, price: BigDecimal, vendor: User)
    : this(-1, name, category, description, price, vendor)

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  @Column(name = "name")
  var name: String = ""

  @ManyToOne
  @JoinColumn(foreignKey = ForeignKey(name = "category_id"), name = "category_id")
  var category: Category = Category()

  @Column(name = "description")
  var description: String = ""

  @Column(name = "price", scale = 15, precision = 4)
  var price: BigDecimal = BigDecimal(0.0)

  @ManyToOne
  @JoinColumn(name = "client_id", referencedColumnName = "id")
  var vendor: User = User()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Product

    if (
      this.id != other.id
      || this.name != other.name
      || this.category.id != other.category.id
      || this.description != other.description
      || this.price != other.price
      || this.vendor.id != other.vendor.id
    ) return false

    return true
  }

  override fun hashCode(): Int {
    return calculateHashCode(
      this.name, this.category.id, this.description, this.price, this.vendor.id
    )
  }

  companion object {
    const val serialVersionUID = 323356734254775807L
  }
}
