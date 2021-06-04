package br.balladesh.icommerce.ecommerce.repository

import br.balladesh.icommerce.ecommerce.entity.Order
import br.balladesh.icommerce.ecommerce.entity.OrderedProduct
import br.balladesh.icommerce.security.entity.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OrderRepository : CrudRepository<Order, Long> {
  fun findAllByUser(user: User): Set<Order>
  fun findAllByPaied(paied: Boolean): Set<Order>
  fun findByIdAndUser(id: Long, user: User): Optional<Order>
}

interface OrderedProductRepository : CrudRepository<OrderedProduct, Long> {
  fun findAllByOrder(order: Order): Set<OrderedProduct>
  fun findByNameAndOrder(name: String, order: Order): OrderedProduct
}
