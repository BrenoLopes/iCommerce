package br.balladesh.icommerce.ecommerce.repository

import br.balladesh.icommerce.ecommerce.entity.Category
import br.balladesh.icommerce.ecommerce.entity.Product
import br.balladesh.icommerce.security.entity.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProductRepository : CrudRepository<Product, Long> {
  fun findByName(name: String): Optional<Product>
  fun findAllByCategory(category: Category): MutableSet<Product>
  fun findAllByVendor(vendor: User): MutableSet<Product>
  fun findAllByIdIn(idSet: Set<Long>): MutableSet<Product>
  fun deleteAllByVendor(vendor: User)
}
