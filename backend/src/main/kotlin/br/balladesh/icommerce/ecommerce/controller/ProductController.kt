package br.balladesh.icommerce.ecommerce.controller

import br.balladesh.icommerce.ecommerce.dto.ProductListResponse
import br.balladesh.icommerce.ecommerce.dto.ProductSignUpRequest
import br.balladesh.icommerce.ecommerce.entity.Product
import br.balladesh.icommerce.ecommerce.repository.CategoryRepository
import br.balladesh.icommerce.ecommerce.repository.ProductRepository
import br.balladesh.icommerce.files.entity.FileEntity
import br.balladesh.icommerce.files.repository.FileRepository
import br.balladesh.icommerce.security.dto.MessageResponse
import br.balladesh.icommerce.security.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping(value = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(
  private val userService: UserService,
  private val categoryRepository: CategoryRepository,
  private val productRepository: ProductRepository,
  private val fileRepository: FileRepository
) {
  val logger: Logger = LoggerFactory.getLogger(this::class.java)

  @PostMapping(value = ["/product"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
  @PreAuthorize("hasRole('ADMIN')")
  fun registerProduct(@ModelAttribute request: ProductSignUpRequest, user: Principal): ResponseEntity<MessageResponse> {
    val fileLocation = fileRepository.save(request.image.bytes, request.image.originalFilename)

    try {
      val currentUser = this.userService.findByUsername(user.name)

      val category = this.categoryRepository.findById(request.category_id)
      if (!category.isPresent) {
        return ResponseEntity.badRequest()
          .body(MessageResponse(HttpStatus.BAD_REQUEST.value(), "A categoria informada não existe!"))
      }

      val image = FileEntity(fileLocation)
      val product = Product(request.name, category.get(), request.description, image, request.price, currentUser)

      this.productRepository.save(product)

      return ResponseEntity.ok(MessageResponse("O produto foi cadastrado com sucesso!"))
    } catch (e: DataIntegrityViolationException) {
      val message = String.format("Um produto com o nome %s já existe!", request.name)
      val status = HttpStatus.CONFLICT

      fileRepository.deleteByLocation(fileLocation.privatePath)

      return ResponseEntity
        .status(status)
        .body(MessageResponse(status, message))
    } catch (e: Exception) {
      logger.error("Ocorreu um erro ao cadastrar um produto", e)

      fileRepository.deleteByLocation(fileLocation.privatePath)

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro no servidor!"))
    }
  }

  @GetMapping(value = ["/product"])
  fun listAllProducts(user: Principal): ResponseEntity<Any> {
    return try {
      val products = this.productRepository.findAll()

      ResponseEntity.ok(ProductListResponse(products.toSet()))
    } catch (e: Exception) {
      logger.error("Ocorreu um erro ao listar os produtos", e)

      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro no servidor!"))
    }
  }
}
