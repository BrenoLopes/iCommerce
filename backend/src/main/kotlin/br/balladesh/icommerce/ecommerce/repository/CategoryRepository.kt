package br.balladesh.icommerce.ecommerce.repository

import br.balladesh.icommerce.ecommerce.entity.Category
import br.balladesh.icommerce.ecommerce.entity.Product
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CategoryRepository : CrudRepository<Category, Long> {
  fun findByName(name: String): Optional<Category>
  fun findByProducts(product: Product): Optional<Category>
}
