package br.balladesh.icommerce.ecommerce.controllers

import br.balladesh.icommerce.ecommerce.dto.ProductSignUpRequest
import br.balladesh.icommerce.ecommerce.entity.Product
import br.balladesh.icommerce.ecommerce.repository.CategoryRepository
import br.balladesh.icommerce.ecommerce.repository.ProductRepository
import br.balladesh.icommerce.security.dto.MessageResponse
import br.balladesh.icommerce.security.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping(value = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(
  private val userService: UserService,
  private val categoryRepository: CategoryRepository,
  private val productRepository: ProductRepository
) {
  val logger: Logger = LoggerFactory.getLogger(this::class.java)

  @PostMapping(value = ["/product"])
  @PreAuthorize("hasRole('ADMIN')")
  fun registerProduct(@RequestBody request: ProductSignUpRequest, user: Principal): ResponseEntity<MessageResponse> {
    try {
      val currentUser = this.userService.findByUsername(user.name)

      val category = this.categoryRepository.findById(request.category_id)

      if (!category.isPresent) {
        return ResponseEntity.badRequest()
          .body(MessageResponse(HttpStatus.BAD_REQUEST.value(), "A categoria informada não existe!"))
      }

      val product = Product(request.name, category.get(), request.description, request.price, currentUser)

      this.productRepository.save(product)

      return ResponseEntity.ok(MessageResponse("O produto foi cadastrado com sucesso!"))
    } catch (e: DataIntegrityViolationException) {
      val message = String.format("Um produto com o nome %s já existe!", request.name)
      val status = HttpStatus.CONFLICT
      return ResponseEntity
        .status(status)
        .body(MessageResponse(status, message))
    } catch (e: Exception) {
      logger.error("Ocorreu um erro ao cadastrar um produto", e)

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro no servidor!"))
    }
  }

}
