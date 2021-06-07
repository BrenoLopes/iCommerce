import react, {useState} from 'react'
import { useHistory } from 'react-router-dom';

import logo from 'iCommerce.svg'

import './NavigationComponent.scss'
import MenuComponent from "../menu/MenuComponent";

const NavigationComponent: react.FC = () => {
  const [showMenu, setShowMenu] = useState(false)
  const history = useHistory()

  const goToHome = () => {
    history.push("/produtos")
  }

  const toggleMenu = () => {
    setShowMenu(!showMenu)
  }

  return (
    <div className="main-navigation">
      <div className="navigation-logo">
        <img src={logo} alt="" onClick={goToHome} />
      </div>
      <div className="navigation-toggle" onClick={toggleMenu} role={"button"}>
        <i className="fa fa-bars" />
      </div>
      <MenuComponent show={showMenu} />
    </div>
  )
}

export default NavigationComponent
