import react from 'react';

import "./MenuComponent.scss"
import React from "react";
import SeparatorComponent from "../separator/SeparatorComponent";

interface MenuComponentProps {
  show?: boolean
}

const MenuComponent: react.FC<MenuComponentProps> = (props) => {
  return (
    <div className={`menu-container ${props.show ? "" : "menu-hide"}`}>
      <div className="menu-user-container">
        <div className="menu-user-img">
          <i className="fas fa-user-alt" />
        </div>
        <div className="menu-user-name">
          BanYasuoFeedersPls
        </div>
      </div>
      <SeparatorComponent label="" color="light" />
      <MenuItemComponent
        iconName="fa-shopping-cart"
        label="Ir para o carrinho"
        color="primary"
        type="button"
        onClick={() => {console.log("Clicked in configurations")}}
      />
      <SeparatorComponent label="" color="light" />
      <MenuItemComponent
        iconName="fa-folder"
        label="Categorias"
        color="primary"
        type="normal"
        onClick={() => {console.log("Clicked in configurations")}}
      />
      <div className="menu-categories-container">
        <MenuCategoryItem label="Celulares" onClick={() => {}} />
        <MenuCategoryItem label="Eletro" onClick={() => {}} />
        <MenuCategoryItem label="Tv e Áudio" onClick={() => {}} />
        <MenuCategoryItem label="Esporte e lazer" onClick={() => {}} />
        <MenuCategoryItem label="Ferramentas e Jardim" onClick={() => {}} />
        <MenuCategoryItem label="Decoração" onClick={() => {}} />
        <MenuCategoryItem label="Automotivo" onClick={() => {}} />
        <MenuCategoryItem label="Moda" onClick={() => {}} />
        <MenuCategoryItem label="Informática" onClick={() => {}} />
        <MenuCategoryItem label="Cama mesa e banho" onClick={() => {}} />
        <MenuCategoryItem label="Utilidades domésticas" onClick={() => {}} />
        <MenuCategoryItem label="Outros" onClick={() => {}} />
      </div>
      <SeparatorComponent label="" color="light" />
      <MenuItemComponent
        iconName="fa-cog"
        label="Configurações"
        color="primary"
        type="button"
        onClick={() => {console.log("Clicked in configurations")}}
      />
      <SeparatorComponent label="" color="light" />
      <MenuItemComponent
        iconName="fa-sign-out-alt"
        label="Sair"
        color="secondary"
        type="button"
        onClick={() => {console.log("Clicked in configurations")}}
      />
    </div>
  )
}

interface MenuItemComponentProps {
  iconName: string,
  label: string,
  onClick: () => void,
  color: "primary" | "secondary"
  type: "button" | "normal"
}

const MenuItemComponent: react.FC<MenuItemComponentProps> = (props) => {
  return (
    <div
      className={`menu-item-container ${props.type === "normal" ? "menu-item-container-div" : ""}`}
      onClick={props.onClick}
      role={`${props.type === "button" ? "button" : "list"}`}
    >
      <div className={`menu-icon ${props.color === "secondary" ? "menu-icon-secondary" : ""}`}>
        <i className={`fas ${props.iconName}`} />
      </div>
      <div className="menu-item-info">
        {props.label}
      </div>
    </div>
  )
}

interface MenuCategoryItemProps {
  label: string,
  onClick: () => void
}

const MenuCategoryItem: react.FC<MenuCategoryItemProps> = (props) => {
  return (
    <div onClick={props.onClick} className="menu-categories-item">{props.label}</div>
  )
}

export default MenuComponent
