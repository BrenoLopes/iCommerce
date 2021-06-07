import React, {useState} from "react";
import {validatePredicates} from "../validators";

import "./FormInputComponent.scss";

interface Props {
  label: string,
  validators: InputPredicate[],
  onChange: (text: string, isValid: boolean) => void,
  type: FormInputType,
}

interface TextInputState {
  errorMessages: string[],
  value: string
}

const FormInputComponent: React.FC<Props> = (props: Props) => {
  const [state, setState] = useState<TextInputState>({
    errorMessages: [],
    value: ""
  })

  const inputClassName: string
    = `input-element ${state.errorMessages.length > 0 ? "border-red" : ""}`

  const checkValidity = (text: string) => {
    const result = validatePredicates(props.validators, text)

    props.onChange(text, result.isValid)
    setState({value: text, errorMessages: result.errorMessages})
  }

  const onInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    checkValidity(event.target.value)
  }

  const onFocus = (event: React.FocusEvent<HTMLInputElement>) => {
    checkValidity(event.target.value)
  }

  return (
    <div className="input-container">
      <div className="input-label">{props.label}</div>
      <input
        className={inputClassName}
        type={props.type}
        value={state.value}
        onChange={onInputChange}
        onFocus={onFocus}
      />
      <div className="input-errors-container">
        {state.errorMessages.map(error => <p className="input-error">{error}</p>)}
      </div>
    </div>
  );
}

export default FormInputComponent;
