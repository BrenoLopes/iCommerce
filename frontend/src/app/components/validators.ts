export const validateEmptyInput: () => InputPredicate = () => {
  return {
    message: "Não pode estar vazio",
    test: (input) => {
      return input === ""
    }
  }
}

export const validateCharLength: (length: number) => InputPredicate = (length) => {
  return {
    message: `Não pode ter mais que ${length} caracteres`,
    test: (input) => {
      return input.length >= length
    }
  }
}

export const validatePredicates: (p: InputPredicate[], t: string) => ValidationResult = (predicates, text) => {
  let isValid = true

  let messagesBuffer: string[] = []
  predicates.forEach(predicate => {
    // if all predicates fails then it is valid
    // if any predicates succeed then it is invalid
    if (!predicate.test(text)) return

    isValid = false
    messagesBuffer.push(predicate.message)
  })

  return {
    isValid,
    errorMessages: messagesBuffer
  };
}
