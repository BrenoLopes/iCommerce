import React, {FunctionComponent} from "react";
import "./Button.scss";

interface Props {
  onClick: () => void;
  isFull: boolean | undefined;
}

const Button: FunctionComponent<Props> = ({ onClick, isFull }: Props): JSX.Element => {
  return (
    <button onClick={onClick} className={`button ${isFull ? "button-full" : ""}`}>Login</button>
  );
}

export default Button
