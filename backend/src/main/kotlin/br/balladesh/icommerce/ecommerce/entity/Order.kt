package br.balladesh.icommerce.ecommerce.entity

import br.balladesh.icommerce.security.entity.User
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order() {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  @ManyToOne
  @JoinColumn(name = "client_id", referencedColumnName = "id")
  var user: User = User()

  @Column(name = "total_price", scale = 15, precision = 4)
  var totalPrice: BigDecimal = BigDecimal(0.0)

  @Column(name = "paied")
  var paied = false

  @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
  var orderedProducts: Set<OrderedProduct> = emptySet()

  constructor(id: Long, user: User, totalPrice: BigDecimal, paied: Boolean): this() {
    this.id = id
    this.user = user
    this.totalPrice = totalPrice
    this.paied = paied
  }

  constructor(user: User, totalPrice: BigDecimal, paied: Boolean): this(-1, user, totalPrice, paied)
}

@Entity
@Table(name = "ordered_products")
class OrderedProduct() {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  var name = ""

  var description = ""

  @Column(name = "unit_price", scale = 15, precision = 4)
  var price = BigDecimal(0.0)

  @ManyToOne
  @JoinColumn(foreignKey = ForeignKey(name = "order_id"), name="order_id")
  var order = Order()

  constructor(id: Long, name: String, description: String, price: BigDecimal, order: Order): this() {
    this.id = id
    this.name = name
    this.description = description
    this.price = price
    this.order = order
  }

  constructor(name: String, description: String, price: BigDecimal, order: Order)
    : this(-1, name, description, price, order)
}
