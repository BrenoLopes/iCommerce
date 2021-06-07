import react, {useEffect, useRef, useState} from 'react';
import Select from "react-select";

import "./ProductInformationComponent.scss"
import {transformPriceIntoString} from "../transformers";

interface ProductInformationProps {
  imageUrl: string,
  name: string,
  price: number,
  quantity: number,
  onPriceChange: (name: string, price: number, quantity: number) => void,
}

interface SelectType {
  value: number,
  label: number
}

const ProductInformationComponent: react.FC<ProductInformationProps> = (props) => {
  const [price, setPrice] = useState(props.price)
  const [quantity, setQuantity] = useState(props.quantity)

  let selectOptions: SelectType[] = [
    { value: 1, label: 1 },
    { value: 2, label: 2 },
    { value: 3, label: 3 },
    { value: 4, label: 4 },
    { value: 5, label: 5 },
    { value: 6, label: 6 },
    { value: 7, label: 7 },
    { value: 8, label: 8 },
    { value: 9, label: 9 },
    { value: 10, label: 10 },
  ]

  useEffect(() => {
    const option = selectOptions.find(o => o.label === props.quantity)
    const quantity = option ? option.label : 1
    setQuantity(quantity)
    setPrice(props.price * quantity)
  }, [props.price, props.quantity, selectOptions])

  const onChange = (option: any | {value: number, label: number}) => {
    const newPrice = props.price * option.value

    setPrice(newPrice)
    props.onPriceChange(props.name, newPrice, option.value)
  }

  return (
    <div className="product-information-container">
      <div className="product-information-header">
        <div className="product-information-image">
          <img className="product-image" src={props.imageUrl} alt="#" />
        </div>
        <div className="product-name">{props.name}</div>
      </div>
      <div className="product-information-data">
        <div className="product-quantity">
          <p>Quantidade</p>
          <Select
            value={selectOptions[quantity - 1]}
            options={selectOptions}
            onChange={onChange}
            defaultValue={{ value: quantity, label: quantity }}
          />
        </div>
        <div className="product-price">
          <p>Por</p>
          <p className="price">{transformPriceIntoString(price)}</p>
        </div>
      </div>
    </div>
  )
}

export default ProductInformationComponent;
