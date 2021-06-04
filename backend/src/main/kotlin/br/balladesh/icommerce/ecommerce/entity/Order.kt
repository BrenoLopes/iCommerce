package br.balladesh.icommerce.ecommerce.entity

import br.balladesh.icommerce.calculateHashCode
import br.balladesh.icommerce.security.entity.User
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import java.math.BigDecimal
import java.math.RoundingMode
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order() {
  constructor(id: Long, user: User, totalPrice: BigDecimal, paied: Boolean) : this() {
    this.id = id
    this.user = user
    this.totalPrice = totalPrice
    this.paied = paied
  }

  constructor(user: User, totalPrice: BigDecimal, paied: Boolean) : this(-1, user, totalPrice, paied)

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  var id: Long = -1

  @ManyToOne
  @JoinColumn(foreignKey = ForeignKey(name = "client_id"), name = "client_id")
  var user: User = User()

  @Column(name = "total_price", scale = 15, precision = 4)
  var totalPrice: BigDecimal = BigDecimal(0.0)

  @Column(name = "paied")
  var paied = false

  @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
  var orderedProducts: MutableSet<OrderedProduct> = mutableSetOf()
    private set

  fun addOrderedProductBidirectionally(orderedProduct: OrderedProduct, shouldSet: Boolean = true) {
    this.orderedProducts.add(orderedProduct)

    if (!shouldSet) return

    orderedProduct.setOrderBidirectionally(this, false)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Order

    if (
      this.id != other.id
      || this.user.id != other.user.id
      || this.totalPrice != other.totalPrice
      || this.paied != other.paied
      || this.orderedProducts != other.orderedProducts
    ) return false

    return true
  }

  override fun hashCode(): Int {
    return calculateHashCode(
      this.id, this.user.id, this.totalPrice, this.paied, this.orderedProducts
    )
  }

  @JsonValue
  fun toJson(): ObjectNode {
    val mapper = ObjectMapper()
    val node: ObjectNode = mapper.createObjectNode()
    node.put("id", id)
      .put("user", user.name)
      .put("total_price", totalPrice.setScale(2, RoundingMode.CEILING))
      .put("paied", paied)
      .putPOJO("ordered_products", orderedProducts)
    return node
  }
}

@Entity
@Table(name = "ordered_products")
class OrderedProduct() {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  var id: Long = -1

  var name = ""

  var description = ""

  @Column(name = "unit_price", scale = 15, precision = 4)
  var price = BigDecimal(0.0)

  @ManyToOne
  @JoinColumn(foreignKey = ForeignKey(name = "order_id"), name = "order_id")
  var order = Order()
    private set

  fun setOrderBidirectionally(order: Order, shouldSet: Boolean = true) {
    this.order = order

    if (!shouldSet) return

    order.addOrderedProductBidirectionally(this, false)
  }

  constructor(id: Long, name: String, description: String, price: BigDecimal, order: Order) : this() {
    this.id = id
    this.name = name
    this.description = description
    this.price = price
    this.setOrderBidirectionally(order)
  }

  constructor(name: String, description: String, price: BigDecimal, order: Order)
    : this(-1, name, description, price, order)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as OrderedProduct

    if (
      this.id != other.id
      || this.name != other.name
      || this.description != other.description
      || this.price != other.price
      || this.order.id != other.order.id
    ) return false

    return true
  }

  override fun hashCode(): Int {
    return calculateHashCode(
      this.id, this.name, this.description, this.price, this.order.id
    )
  }

  @JsonValue
  fun toJson(): ObjectNode {
    val mapper = ObjectMapper()
    val node: ObjectNode = mapper.createObjectNode()
    node.put("id", id)
      .put("name", name)
      .put("description", description)
      .put("price", price.setScale(2, RoundingMode.CEILING))
      .put("order_id", order.id)
    return node
  }
}
