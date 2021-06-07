import react from "react";

import './SeparatorComponent.scss'

interface Props {
  label: string,
  color: "dark" | "light",
}

const SeparatorComponent: react.FC<Props> = ( {label, color} ) => {
  const separatorClass = `separator ${color === "dark" ? "" : "separator-light"}`;

  return (
    <div className="separator-container">
      <div className={separatorClass} />
      <div className={`label ${label === "" ? "hide" : ""}`}>{label}</div>
      <div className={separatorClass} />
    </div>
  )
}

export default SeparatorComponent
