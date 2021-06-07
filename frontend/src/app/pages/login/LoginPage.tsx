import react, { useEffect, useState } from 'react'
import { useHistory } from 'react-router-dom'
import logo from 'iCommerce.svg'

import { validateCharLength, validateEmptyInput } from "../../components/validators";
import FormInputComponent from "app/components/forminput/FormInputComponent";
import CheckboxInput from "app/components/checkbox/CheckboxComponent";
import ButtonComponent from "app/components/button/ButtonComponent";
import SeparatorComponent from "app/components/separator/SeparatorComponent";

import "./LoginPage.scss"

interface LoginPageProps {

}

const LoginPage : react.FC<LoginPageProps> = () => {
  const [userInputData, setUserInputData] = useState({
    user: "",
    isValid: false
  })
  const [userPasswordData, setUserPasswordData] = useState({
    user: "",
    isValid: false
  })
  const [rememberPassword, setRememberPassword] = useState(true)
  const [isFormDisabled, setIsFormDisabled] = useState(true)

  useEffect(() => {
    if (userInputData.isValid && userPasswordData.isValid)
      setIsFormDisabled(false)
    else
      setIsFormDisabled(true)
  }, [userInputData, userPasswordData])

  const history = useHistory()

  const onLogin = () => {

  }

  const onSignUp = () => {
    history.push("/cadastrar")
  }

  return (
    <div className="login-page-container">
      <div className="page-top" />
      <div className="form-container">
        <div className="website-logo">
          <img src={logo} alt=""/>
        </div>
        <FormInputComponent
          label="Usuário"
          validators={[validateEmptyInput(), validateCharLength(50)]}
          onChange={(text, isValid) => setUserInputData({
            user: text,
            isValid
          })}
          type="text"
        />
        <FormInputComponent
          label="Senha"
          validators={[
            validateEmptyInput(),
            validateCharLength(50),
            // { message: "A senha não pode ser menor que 6 caracteres", test: (text) => text.length < 7 }
          ]}
          onChange={(text, isValid) => setUserPasswordData({
            user: text,
            isValid
          })}
          type="password"
        />
        <CheckboxInput
          default={rememberPassword}
          onChange={(state) => {
            setRememberPassword(state)
          }}
          label={"Lembrar senha"}
        />
        <ButtonComponent label="Login" isFull={true} color="primary" onClick={onLogin} isDisabled={isFormDisabled} />
        <SeparatorComponent label="ou" color="dark" />
        <ButtonComponent label="Cadastrar" isFull={true} color="default" onClick={onSignUp} isDisabled={false} />
      </div>
    </div>
  )
}

export default LoginPage
