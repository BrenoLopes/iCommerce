package br.balladesh.icommerce.files.entity

import br.balladesh.icommerce.calculateHashCode
import br.balladesh.icommerce.files.dto.SavedFile
import javax.persistence.*

@Entity
@Table(name = "images")
class FileEntity() {
  constructor(id: Long, name: String, location: String) : this() {
    this.id = id
    this.name = name
    this.location = location
  }

  constructor(savedFile: SavedFile) : this() {
    this.name = savedFile.fileName
    this.location = savedFile.publicPath
  }

  @Column(name = "ID", nullable = false)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  @Column(length = 255)
  var name: String = ""

  @Column(length = 500)
  var location: String = ""

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as FileEntity

    if (id != other.id) return false
    if (name != other.name) return false
    if (location != other.location) return false

    return true
  }

  override fun hashCode(): Int {
    return calculateHashCode(id, name, location)
  }
}
