package br.balladesh.icommerce.ecommerce.controllers

import br.balladesh.icommerce.ecommerce.dto.OrderListResponse
import br.balladesh.icommerce.ecommerce.dto.PayOrderRequest
import br.balladesh.icommerce.ecommerce.dto.PostOrderRequest
import br.balladesh.icommerce.ecommerce.entity.Order
import br.balladesh.icommerce.ecommerce.entity.OrderedProduct
import br.balladesh.icommerce.ecommerce.repository.OrderRepository
import br.balladesh.icommerce.ecommerce.repository.ProductRepository
import br.balladesh.icommerce.security.dto.MessageResponse
import br.balladesh.icommerce.security.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.security.Principal

@RestController
@RequestMapping(value = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
class OrderController(
  private val userService: UserService,
  private val orderRepository: OrderRepository,
  private val productRepository: ProductRepository
) {
  val logger: Logger = LoggerFactory.getLogger(this.javaClass)

  @PostMapping(value = ["/order"])
  fun orderProducts(@RequestBody request: PostOrderRequest, user: Principal): ResponseEntity<MessageResponse> {
    try {
      val currentUser = this.userService.findByUsername(user.name)

      val products = productRepository.findAllByIdIn(request.products)

      var totalPrice = BigDecimal(0.0)

      val order = Order(currentUser, totalPrice, false)
      val orderedProducts: MutableSet<OrderedProduct> = mutableSetOf()

      products.forEach{
        orderedProducts.add(OrderedProduct(it.name, it.description, it.price, order))
        totalPrice = totalPrice.add(it.price)
      }

      order.totalPrice = totalPrice

      this.orderRepository.save(order)

      return ResponseEntity.ok(MessageResponse("A sua ordem foi feita com sucesso!"))
    } catch (e: Exception) {
      logger.error("Ocorreu um erro ao postar uma ordem", e)

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro no servidor!"))
    }
  }

  @PostMapping(value = ["/order/pay"])
  fun payOrderMadeByClient(@RequestBody request: PayOrderRequest, user: Principal): ResponseEntity<Any> {
    return try {
      val currentUser = this.userService.findByUsername(user.name)
      val order = orderRepository.findByIdAndUser(request.order_id, currentUser)

      if (!order.isPresent) {
        throw RuntimeException("Pedido não encontrado")
      }

      if (order.get().paied) {
        return ResponseEntity.ok(MessageResponse("O pedido já está pago!"))
      }

      order.get().paied = true
      orderRepository.save(order.get())

      ResponseEntity.ok(MessageResponse("O pedido foi pago com sucesso!"))
    } catch(e: RuntimeException) {
      ResponseEntity.badRequest().body(MessageResponse(HttpStatus.BAD_REQUEST, "O pedido não existe!"))
    } catch (e: Exception) {
      logger.error("Ocorreu um erro ao listar as ordens", e)

      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro no servidor!"))
    }
  }

  @GetMapping(value = ["/order"])
  fun listAllOrdersMadeByClient(user: Principal): ResponseEntity<Any> {
    return try {
      val currentUser = this.userService.findByUsername(user.name)
      val orders = orderRepository.findAllByUser(currentUser)

      ResponseEntity.ok(OrderListResponse(orders.toSet()))
    } catch (e: Exception) {
      logger.error("Ocorreu um erro ao listar as ordens", e)

      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro no servidor!"))
    }
  }

  @GetMapping(value = ["/order/all"])
  @PreAuthorize("hasRole('ADMIN')")
  fun listAllOrders(): ResponseEntity<Any>  {
    return try {
      val orders = orderRepository.findAll()

      ResponseEntity.ok(OrderListResponse(orders.toSet()))
    } catch (e: Exception) {
      logger.error("Ocorreu um erro ao listar as ordens", e)

      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro no servidor!"))
    }
  }
}
