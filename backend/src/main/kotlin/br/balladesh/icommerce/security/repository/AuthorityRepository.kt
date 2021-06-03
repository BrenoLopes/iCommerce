package br.balladesh.icommerce.security.repository

import br.balladesh.icommerce.security.entity.Authority
import br.balladesh.icommerce.security.entity.UserRoleName
import org.springframework.data.repository.CrudRepository

interface AuthorityRepository : CrudRepository<Authority, Long> {
  fun findByName(name: UserRoleName): Authority
}
