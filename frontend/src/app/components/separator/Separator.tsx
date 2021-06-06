import react from "react";

import './Separator.scss'

interface Props {
  label: string
}

const SeparatorComponent: react.FC<Props> = ( {label} ) => {
  return (
    <div className="separator-container">
      <div className="separator" />
      <div className={`label ${label === "" ? "hide" : ""}`}>{label}</div>
      <div className="separator" />
    </div>
  )
}

export default SeparatorComponent
