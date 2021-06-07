import {ActionType} from "typesafe-actions";
import * as actions from "./actions";

export interface UserState {
  username: string
}

export type UserActions = ActionType<typeof actions>
