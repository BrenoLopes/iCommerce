package br.balladesh.icommerce.ecommerce.dto

import br.balladesh.icommerce.ecommerce.entity.Product

data class ProductListResponse(
  val products: Set<Product>
)
