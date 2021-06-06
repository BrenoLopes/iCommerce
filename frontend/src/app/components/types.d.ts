interface InputPredicate {
  test: (inputText: string) => boolean
  message: string
}

interface ValidationResult {
  isValid: boolean,
  errorMessages: string[],
}

type FormInputType = "text" | "password"
