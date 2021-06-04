package br.balladesh.icommerce.ecommerce.controllers

import br.balladesh.icommerce.ecommerce.dto.CategoryListResponse
import br.balladesh.icommerce.ecommerce.repository.CategoryRepository
import br.balladesh.icommerce.security.dto.MessageResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CategoryController(private val categoryRepository: CategoryRepository) {
  val logger: Logger = LoggerFactory.getLogger(this::class.java)

  @GetMapping("/categories")
  fun listAllCategories(): ResponseEntity<Any> {
    return try {
      val categories = this.categoryRepository.findAll()

      ResponseEntity.ok(CategoryListResponse(categories.iterator().asSequence().toSet()))
    } catch (e: Exception) {
      logger.error("Ocorreu um erro ao listar as categorias", e)

      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro no servidor!"))
    }
  }
}
