package br.balladesh.icommerce.ecommerce.dto

import java.math.BigDecimal

data class ProductSignUpRequest(
  val name: String,
  val category_id: Long,
  val description: String,
  val price: BigDecimal
)
