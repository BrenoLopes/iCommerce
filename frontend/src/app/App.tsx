import React from 'react';
import { Switch, Route } from 'react-router-dom';

import LoginPage from "app/pages/login/LoginPage";
import SignupPage from "app/pages/signup/SignupPage";

import './App.scss';

function App() {
  return (
    <div className="app-root">
      <Switch>
        <Route path="/" component={LoginPage} exact />
        <Route path="/cadastrar" component={SignupPage} />
      </Switch>
    </div>
  )
}

export default App;
