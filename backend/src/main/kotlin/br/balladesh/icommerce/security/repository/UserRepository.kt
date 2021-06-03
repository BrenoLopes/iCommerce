package br.balladesh.icommerce.security.repository

import br.balladesh.icommerce.security.entity.User
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UserRepository : CrudRepository<User, Long> {
  fun findByUsername(username: String): Optional<User>
}
