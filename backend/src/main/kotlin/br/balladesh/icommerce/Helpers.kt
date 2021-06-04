package br.balladesh.icommerce

fun calculateHashCode(vararg elements: Any): Int {
  var result = -1;

  for(element in elements) {
    if (result == -1) {
      result = element.hashCode()
    } else {
      result = 31 * result + (element.hashCode())
    }
  }

  return result
}
