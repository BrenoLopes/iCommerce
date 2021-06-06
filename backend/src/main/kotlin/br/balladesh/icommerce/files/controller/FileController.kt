package br.balladesh.icommerce.files.controller

import br.balladesh.icommerce.files.repository.FileRepository
import org.apache.commons.io.FilenameUtils
import org.springframework.core.io.FileSystemResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

data class ImageResponse(
  val id: Long,
  val name: String,
  val location: String
)

data class ImageRequest(
  val image: MultipartFile
)

@RestController
@RequestMapping(value = ["/api"])
class FileController(
  private val repository: FileRepository
) {
  @PostMapping("/image")
  fun uploadFile(@ModelAttribute request: ImageRequest): ResponseEntity<ImageResponse> {
    val ext = FilenameUtils.getExtension(request.image.originalFilename)

    val newFilePath = this.repository.save(request.image.bytes, ext)

    return ResponseEntity.ok(ImageResponse(1, newFilePath.publicPath, ext))
  }

  @GetMapping("/image/{fileName}")
  fun downloadFile(@PathVariable fileName: String): FileSystemResource {
    return this.repository.findByLocation(fileName)
  }
}
