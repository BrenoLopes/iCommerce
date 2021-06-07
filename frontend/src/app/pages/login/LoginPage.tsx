import react, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import logo from "iCommerce.svg";

import {
  validateCharLength,
  validateEmptyInput,
} from "../../components/validators";
import FormInputComponent from "app/components/forminput/FormInputComponent";
import CheckboxInput from "app/components/checkbox/CheckboxComponent";
import ButtonComponent from "app/components/button/ButtonComponent";
import SeparatorComponent from "app/components/separator/SeparatorComponent";

import "./LoginPage.scss";
import Network from "../../networking/url";
import { LoginRequest, LoginResponse } from "./types";

const LoginPage: react.FC = () => {
  const [username, setUsername] = useState({
    user: "",
    isValid: false,
  });
  const [userPasswordData, setUserPasswordData] = useState({
    user: "",
    isValid: false,
  });
  const [rememberPassword, setRememberPassword] = useState(true);
  const [isFormDisabled, setIsFormDisabled] = useState(true);

  const history = useHistory();

  useEffect(() => {
    if (localStorage.getItem('token') === null) return

    history.push("/produtos")
  }, [])

  useEffect(() => {
    if (username.isValid && userPasswordData.isValid) setIsFormDisabled(false);
    else setIsFormDisabled(true);
  }, [username, userPasswordData]);

  const onLogin = async () => {
    const loginData: LoginRequest = {
      username: username.user,
      password: userPasswordData.user,
    };

    try {
      const result = await new Network<LoginResponse>(false).post(
        "/auth/login",
        loginData,
        {
          headers: { "Content-Type": "application/json" },
        }
      );

      localStorage.setItem("token", result.data.access_token);

      history.push("/produtos");
    } catch (e) {}
  };

  const onSignUp = () => {
    history.push("/cadastrar");
  };

  return (
    <div className="login-page-container">
      <div className="page-top" />
      <div className="form-container">
        <div className="website-logo">
          <img src={logo} alt="" />
        </div>
        <FormInputComponent
          label="Usuário"
          validators={[validateEmptyInput(), validateCharLength(50)]}
          onChange={(text, isValid) =>
            setUsername({
              user: text,
              isValid,
            })
          }
          type="text"
        />
        <FormInputComponent
          label="Senha"
          validators={[
            validateEmptyInput(),
            validateCharLength(50),
            // { message: "A senha não pode ser menor que 6 caracteres", test: (text) => text.length < 7 }
          ]}
          onChange={(text, isValid) =>
            setUserPasswordData({
              user: text,
              isValid,
            })
          }
          type="password"
        />
        <CheckboxInput
          default={rememberPassword}
          onChange={(state) => {
            setRememberPassword(state);
          }}
          label={"Lembrar senha"}
        />
        <ButtonComponent
          label="Login"
          isFull={true}
          color="primary"
          onClick={onLogin}
          isDisabled={isFormDisabled}
        />
        <SeparatorComponent label="ou" color="dark" />
        <ButtonComponent
          label="Cadastrar"
          isFull={true}
          color="default"
          onClick={onSignUp}
          isDisabled={false}
        />
      </div>
    </div>
  );
};

export default LoginPage;
