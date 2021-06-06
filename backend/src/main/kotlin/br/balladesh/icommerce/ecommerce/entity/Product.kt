package br.balladesh.icommerce.ecommerce.entity

import br.balladesh.icommerce.calculateHashCode
import br.balladesh.icommerce.files.entity.FileEntity
import br.balladesh.icommerce.security.entity.User
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "product")
class Product() : Serializable
{
  constructor(id: Long, name: String, category: Category, description: String, image: FileEntity,  price: BigDecimal, vendor: User) : this() {
    this.id = id
    this.name = name
    this.setCategoryBidirectionally(category)
    this.image = image
    this.description = description
    this.price = price
    this.vendor = vendor
  }

  constructor(name: String, category: Category, description: String, image: FileEntity, price: BigDecimal, vendor: User)
    : this(-1, name, category, description, image, price, vendor)

  @Id
  @Column(name = "id", unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  @Column(name = "name", unique = true, length = 100)
  var name: String = ""

  @ManyToOne
  @JoinColumn(foreignKey = ForeignKey(name = "category_id"), name = "category_id")
  var category: Category = Category()
    private set

  fun setCategoryBidirectionally(category: Category, shouldSet: Boolean = true) {
    this.category = category

    if (!shouldSet) return

    category.addProductBidirectionally(this, false)
  }

  @Column(name = "description")
  var description: String = ""

  @Column(name = "price", scale = 15, precision = 4)
  var price: BigDecimal = BigDecimal(0.0)

  @ManyToOne
  @JoinColumn(name = "client_id", referencedColumnName = "id")
  var vendor: User = User()

  @OneToOne(cascade = [CascadeType.ALL])
  @JoinColumn(foreignKey = ForeignKey(name = "image_id"), name = "image_id")
  var image: FileEntity = FileEntity()

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

  @JsonValue
  fun toJson(): ObjectNode {
    val mapper = ObjectMapper()
    val node: ObjectNode = mapper.createObjectNode()
    node.put("id", id)
      .put("name", name)
      .put("description", description)
      .put("price", price)
      .put("vendor", vendor.name)
      .put("image", this.image.location)
      .put("category_id", category.id)
      .put("category_name", category.name)
    return node
  }

  companion object {
    const val serialVersionUID = 323356734254775807L
  }
}
