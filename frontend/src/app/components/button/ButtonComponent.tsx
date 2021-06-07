import React, {FunctionComponent} from "react";
import "./ButtonComponent.scss";

type Color = "primary" | "secondary" | "default"

interface Props {
  onClick: () => void,
  isFull: boolean,
  label: string,
  color: Color,
  isDisabled: boolean
}

const ButtonComponent: FunctionComponent<Props> = (props: Props): JSX.Element => {

  const buttonFull = props.isFull ? "btn-full" : ""
  const colorCss = loadCssColor(props.color)

  return (
    <button
      type="button"
      onClick={props.onClick}
      className={`btn ${buttonFull} ${colorCss}`}
      disabled={props.isDisabled}
    >
      {props.label}
    </button>
  );
}

const loadCssColor = (color: Color) => {
  switch (color) {
    case "primary":
      return "btn-primary"
    case "secondary":
      return "btn-secondary"
    default:
      return ""
  }
}

export default ButtonComponent
