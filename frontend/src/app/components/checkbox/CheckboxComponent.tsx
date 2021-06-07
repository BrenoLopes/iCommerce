import React, { useState } from "react";

import "./CheckboxComponent.scss"

interface Props {
  default: boolean,
  label: string,
  onChange: (isChecked: boolean) => void
}

const CheckboxInput: React.FC<Props> = (props: Props) => {
  const [state, setState] = useState({checked: props.default})

  const onClick = () => {
    const newState = !state.checked

    setState({checked: newState})
    props.onChange(newState)
  }

  return (
    <div className="checkbox-container" onClick={onClick} role="button">
      <div className={`checkbox ${state.checked ? "checkbox-checked" : ""}`}>
        <i className="fas fa-check" />
      </div>
      <p className="label">{props.label}</p>
    </div>
  )
}

export default CheckboxInput
