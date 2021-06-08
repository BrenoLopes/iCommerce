import react, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";

import logo from "iCommerce.svg";

import {
  validateCharLength,
  validateEmptyInput,
} from "app/components/validators";
import FormInputComponent from "../../components/forminput/FormInputComponent";
import ButtonComponent from "app/components/button/ButtonComponent";
import SeparatorComponent from "app/components/separator/SeparatorComponent";

import "./SignupPage.scss";
import CalendarInputComponent from "../../components/calendarinput/CalendarInputComponent";
import axios, {AxiosError} from "axios";
import {LoginResponse} from "../login/types";
import {getFullUrl} from "../../networking/url";
import {SignUpRequest} from "./types";




const SignupPage: react.FC = () => {
  const [nameData, setNameData] = useState({
    user: "",
    isValid: false,
  });
  const [usernameData, setUsernameData] = useState({
    user: "",
    isValid: false,
  });
  const [userPasswordData, setUserPasswordData] = useState({
    user: "",
    isValid: false,
  });
  const [calendarData, setCalendarData] = useState({
    date: "",
    isValid: true,
  });
  const [isFormDisabled, setIsFormDisabled] = useState(true);

  useEffect(() => {
    if (nameData.isValid && usernameData.isValid && userPasswordData.isValid)
      setIsFormDisabled(false);
    else setIsFormDisabled(true);
  }, [nameData, usernameData, userPasswordData]);

  const history = useHistory();

  const onLogin = () => {
    history.push("/");
  };

  const onSignUp = () => {
    const signupData: SignUpRequest = {
      name: nameData.user,
      username: usernameData.user,
      password: userPasswordData.user
    }

    axios.post<{}>(
      getFullUrl("/auth/signup"),
      signupData,
      {
        headers: {"Content-Type": "application/json"},
      }
    ).then(r => {
      alert("O usuário foi criado com sucesso")
    }).catch(e => {
      const ex = e as AxiosError;
      const status = ex.response?.status

      if (status === 409)
        alert("O usuário já existe ou ocorreu um erro")
      else
        alert("Ocorreu um erro ao contatar o servidor")
    })
  };

  return (
    <div className="signup-page-container">
      <div className="page-top" />
      <div className="form-container">
        <div className="website-logo">
          <img src={logo} alt="" />
        </div>
        <FormInputComponent
          label="Nome"
          validators={[validateEmptyInput(), validateCharLength(50)]}
          onChange={(text, isValid) =>
            setNameData({
              user: text,
              isValid,
            })
          }
          type="text"
        />
        <FormInputComponent
          label="Usuário"
          validators={[validateEmptyInput(), validateCharLength(50)]}
          onChange={(text, isValid) =>
            setUsernameData({
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
        <CalendarInputComponent
          label={"Idade"}
          validators={[
            {
              message: "Você deve ter mais que 18 anos",
              test: (date) => {
                if (Number.isNaN(new Date(date).getFullYear())) return false

                const years = new Date().getFullYear() + new Date(date).getFullYear()
                return years >= 18;
              },
            },
          ]}
          onChange={(date) => {
            console.log("onChange", date);
          }}
        />
        <ButtonComponent
          label="Cadastrar"
          isFull={true}
          color="primary"
          onClick={onSignUp}
          isDisabled={isFormDisabled}
        />
        <SeparatorComponent label="ou" color="dark" />
        <ButtonComponent
          label="Login"
          isFull={true}
          color="default"
          onClick={onLogin}
          isDisabled={false}
        />
      </div>
    </div>
  );
};

export default SignupPage;
