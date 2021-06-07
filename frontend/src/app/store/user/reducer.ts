import { UserState, UserActions } from "./types";
import { Constants } from "./constants";

const init: UserState = {
  username: "",
};

export function userReducer(state: UserState = init, action: UserActions): UserState {
  switch (action.type) {
    case Constants.SET_USER:
      return { username: action.payload.username }
    default:
      return state;
  }
}
