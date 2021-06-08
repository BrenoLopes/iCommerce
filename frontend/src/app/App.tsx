import React, {useEffect} from "react";
import { useDispatch, useSelector } from "react-redux";
import { Switch, Route } from "react-router-dom";

import { RootState } from "app/store";
import { UserState, UserActions } from "app/store/user/types";
import { Constants } from "app/store/user/constants";

import LoginPage from "app/pages/login/LoginPage";
import SignupPage from "app/pages/signup/SignupPage";
import ProductsPage from "./pages/products/ProductsPage";

import "./App.scss";

function App() {
  const user: UserState = useSelector((state: RootState) => state.user);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch<UserActions>({
      type: Constants.SET_USER,
      payload: { username: "OhNo" },
    });
  }, [dispatch]);

  return (
    <div className="app-root">
      <Switch>
        <Route path="/" component={LoginPage} exact />
        <Route path="/cadastrar" component={SignupPage} />
        <Route path="/produtos" component={ProductsPage} />
      </Switch>
    </div>
  );
}

export default App;
