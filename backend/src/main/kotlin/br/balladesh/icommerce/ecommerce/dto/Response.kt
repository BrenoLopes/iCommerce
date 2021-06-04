package br.balladesh.icommerce.ecommerce.dto

import br.balladesh.icommerce.ecommerce.entity.Category
import br.balladesh.icommerce.ecommerce.entity.Product

data class CategoryListResponse(
  val categories: Set<Category>
)

data class ProductListResponse(
  val products: Set<Product>
)
