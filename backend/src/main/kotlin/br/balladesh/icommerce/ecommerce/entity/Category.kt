package br.balladesh.icommerce.ecommerce.entity

import br.balladesh.icommerce.calculateHashCode
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "category")
class Category() : Serializable {

  constructor(id: Long, name: String) : this() {
    this.id = id
    this.name = name
  }

  constructor(name: String) : this(-1, name)

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  @Column(name = "name")
  var name: String = ""

  @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
  var products: MutableSet<Product> = mutableSetOf()
    private set

  fun addProductBidirectionally(product: Product, shouldSet: Boolean = true) {
    this.products.add(product)

    if (!shouldSet) return

    product.setCategoryBidirectionally(this, false)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Category

    if (
        this.id != other.id
        || this.name != other.name
    ) return false

    return true
  }

  override fun hashCode(): Int {
    return calculateHashCode(this.id, this.name)
  }

  companion object {
    const val serialVersionUID = 323356786352345807L
  }
}
