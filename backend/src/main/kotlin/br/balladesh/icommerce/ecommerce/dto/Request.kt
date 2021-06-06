package br.balladesh.icommerce.ecommerce.dto

import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal

data class ProductSignUpRequest(
  val name: String,
  val category_id: Long,
  val description: String,
  val price: BigDecimal,
  val image: MultipartFile
)

data class OrderRow (
  val product_id: Long,
  val quantity: Int
)

data class PostOrderRequest(
  val products: Set<OrderRow>
)

data class PayOrderRequest(
  val order_id: Long
)
