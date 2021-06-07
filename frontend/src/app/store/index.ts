

import {UserState} from "./user/types";
import {createStore, combineReducers} from "redux";
import {userReducer} from "./user/reducer";

export interface RootState {
  user: UserState,
}

const store = createStore<RootState, any, any, any>(
  combineReducers({ user: userReducer }),
  // @ts-ignore
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

export default store
