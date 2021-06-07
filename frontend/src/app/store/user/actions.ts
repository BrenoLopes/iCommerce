import { action } from "typesafe-actions";
import { Constants } from "./constants";

export function setUsername(username: string) {
  return action(Constants.SET_USER, { username })
}
