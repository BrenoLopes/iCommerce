package br.balladesh.icommerce.files.repository

import br.balladesh.icommerce.files.dto.SavedFile
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteIfExists
import kotlin.io.path.notExists

@Repository
class FileRepository {
  private val imagesDirectory = Paths.get("./images")

  fun save(bytes: ByteArray, extension: String?): SavedFile {
    if (imagesDirectory.notExists()) {
      imagesDirectory.createDirectories()
    }

    val newFileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(40), extension)

//    val newFilePath: String = StringBuilder()
//      .append(imagesDirectory.toAbsolutePath().toString())
//      .append(FileSystems.getDefault().separator)
//      .append(newFileName)
//      .append(".")
//      .append(extension)
//      .toString()

    val newFile = Paths.get(imagesDirectory.toString(), newFileName)
    Files.write(newFile, bytes)

    return SavedFile(
      String.format("/api/image/%s", newFileName),
      newFile.toAbsolutePath().toString(),
      newFileName
    )
  }

  fun findByLocation(fileName: String): FileSystemResource {
    try {
      return FileSystemResource(Paths.get(imagesDirectory.toString(), fileName))
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }

  fun deleteByLocation(imageLocation: String) {
    try {
      Paths.get(imageLocation).deleteIfExists()
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }
}
